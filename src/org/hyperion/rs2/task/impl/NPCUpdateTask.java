package org.hyperion.rs2.task.impl;

import java.util.Iterator;

import org.hyperion.rs2.GameEngine;
import org.hyperion.rs2.model.Entity;
import org.hyperion.rs2.model.Location;
import org.hyperion.rs2.model.NPC;
import org.hyperion.rs2.model.Player;
import org.hyperion.rs2.model.UpdateFlags;
import org.hyperion.rs2.model.World;
import org.hyperion.rs2.model.UpdateFlags.UpdateFlag;
import org.hyperion.rs2.net.Packet;
import org.hyperion.rs2.net.PacketBuilder;
import org.hyperion.rs2.task.Task;

/**
 * A task which creates and sends the NPC update block.
 * @author Graham Edgecombe
 *
 */
public class NPCUpdateTask implements Task {
	
	/**
	 * The player.
	 */
	private Player player;
	
	/**
	 * Creates an npc update task.
	 * @param player The player.
	 */
	public NPCUpdateTask(Player player) {
		this.player = player;
	}

	@Override
	public void execute(GameEngine context) {
		PacketBuilder updateBlock = new PacketBuilder();
		PacketBuilder packet = new PacketBuilder(71, Packet.Type.VARIABLE_SHORT);
		packet.startBitAccess();
		packet.putBits(8, player.getLocalNPCs().size());
		for(Iterator<NPC> it$ = player.getLocalNPCs().iterator(); it$.hasNext();) {
			NPC npc = it$.next();
			if(World.getWorld().getNPCs().contains(npc) && !npc.isTeleporting() && npc.getLocation().isWithinDistance(player.getLocation())) {
				updateNPCMovement(packet, npc);
				if(npc.getUpdateFlags().isUpdateRequired()) {
					updateNPC(updateBlock, npc);
				}
			} else {
				it$.remove();
				packet.putBits(1, 1);
				packet.putBits(2, 3);
			}
		}
		for(NPC npc : World.getWorld().getRegionManager().getLocalNpcs(player)) {
			if(player.getLocalNPCs().size() >= 255) {
				break;
			}
			if(player.getLocalNPCs().contains(npc)) {
				continue;
			}
			player.getLocalNPCs().add(npc);
			addNewNPC(packet, npc);
			if(npc.getUpdateFlags().isUpdateRequired()) {
				updateNPC(updateBlock, npc);
			
			}
		}
		if(!updateBlock.isEmpty()) {
			packet.putBits(14, 16383);
			packet.finishBitAccess();
			packet.put(updateBlock.toPacket().getPayload());
		} else {
			packet.finishBitAccess();
		}
		player.write(packet.toPacket());
	}

	/**
	 * Adds a new NPC.
	 * @param packet The main packet.
	 * @param npc The npc to add.
	 */
	private void addNewNPC(PacketBuilder packet, NPC npc) {
		packet.putBits(14, npc.getIndex());
		int yPos = npc.getLocation().getY() - player.getLocation().getY();
		int xPos = npc.getLocation().getX() - player.getLocation().getX();
		packet.putBits(1, 0);
		packet.putBits(5, yPos);
		packet.putBits(5, xPos);
		packet.putBits(1, npc.getUpdateFlags().isUpdateRequired() ? 1 : 0);
		packet.putBits(13, npc.getDefinition().getId());		

	}

	/**
	 * Update an NPC's movement.
	 * @param packet The main packet.
	 * @param npc The npc.
	 */
	private void updateNPCMovement(PacketBuilder packet, NPC npc) {		
		/*
		 * Check if the NPC is running.
		 */
		if(npc.getSprites().getSecondarySprite() == -1) {
			/*
			 * They are not, so check if they are walking.
			 */
			if(npc.getSprites().getPrimarySprite() == -1) {
				/*
				 * They are not walking, check if the NPC needs an update.
				 */
				if(npc.getUpdateFlags().isUpdateRequired()) {
					/*
					 * Indicate an update is required.
					 */
					packet.putBits(1, 1);
					
					/*
					 * Indicate we didn't move.
					 */
					packet.putBits(2, 0);
				} else {
					/*
					 * Indicate no update or movement is required.
					 */
					packet.putBits(1, 0);
				}
			} else {
				/*
				 * They are walking, so indicate an update is required.
				 */
				packet.putBits(1, 1);
				
				/*
				 * Indicate the NPC is walking 1 tile.
				 */
				packet.putBits(2, 1);
				
				/*
				 * And write the direction.
				 */
				packet.putBits(3, npc.getSprites().getPrimarySprite());
				
				/*
				 * And write the update flag.
				 */
				packet.putBits(1, npc.getUpdateFlags().isUpdateRequired() ? 1 : 0);
			}
		} else {
			/*
			 * They are running, so indicate an update is required.
			 */
			packet.putBits(1, 1);

			/*
			 * Indicate the NPC is running 2 tiles.
			 */
			packet.putBits(2, 2);
			
			/*
			 * And write the directions.
			 */
			packet.putBits(3, npc.getSprites().getPrimarySprite());
			packet.putBits(3, npc.getSprites().getSecondarySprite());
			
			/*
			 * And write the update flag.
			 */
			packet.putBits(1, npc.getUpdateFlags().isUpdateRequired() ? 1 : 0);
		}
	}
	
	/**
	 * Update an NPC.
	 * @param packet The update block.
	 * @param npc The npc.
	 */
	private void updateNPC(PacketBuilder packet, NPC npc) {
		/*
		 * Calculate the mask.
		 */
		int mask = 0;
		final UpdateFlags flags = npc.getUpdateFlags();
		if(flags.get(UpdateFlag.TRANSFORM)) {
			mask |= 0x1;
		}
		if(flags.get(UpdateFlag.FACE_ENTITY)) {
			mask |= 0x40;
		}
		if(flags.get(UpdateFlag.HIT)) {
			mask |= 0x80;
		}
		if(flags.get(UpdateFlag.GRAPHICS)) {
			mask |= 0x4;
		}
		if(flags.get(UpdateFlag.FORCED_CHAT)) {
			mask |= 0x20;
		}
		if(flags.get(UpdateFlag.FACE_COORDINATE)) {
			mask |= 0x8;
		}
		if(flags.get(UpdateFlag.ANIMATION)) {
			mask |= 0x2;
		}
		if(flags.get(UpdateFlag.HIT_2)) {
			mask |= 0x10;
		}		
		/*
		 * And write the mask.
		 */
		packet.put((byte) mask);
		
		if(flags.get(UpdateFlag.TRANSFORM)) {
			// Byte
		}
		if(flags.get(UpdateFlag.FACE_ENTITY)) {
			Entity entity = npc.getInteractingEntity();
			packet.putLEShort(entity == null ? -1 : entity.getClientIndex());
		}
		if(flags.get(UpdateFlag.HIT)) {
			packet.putByteA(npc.getDamage().getHitDamage1());
			packet.putByteA(npc.getDamage().getHitType1());
			packet.put((byte) npc.getHealth()); // Change this to read from defs
			packet.putByteS((byte) npc.getMaxHealth());
		}
		if(flags.get(UpdateFlag.GRAPHICS)) {
			packet.putShort(npc.getCurrentGraphic().getId());
			packet.putLEInt(npc.getCurrentGraphic().getDelay());
		}
		if(flags.get(UpdateFlag.FORCED_CHAT)) {
			packet.putRS2String(""); // npc.getForcedChat(); etc...
		}
		if(flags.get(UpdateFlag.FACE_COORDINATE)) {
			Location loc = npc.getFaceLocation();
			if(loc == null) {
				packet.putLEShortA(0);
				packet.putLEShort(0);
			} else {
				packet.putLEShortA(loc.getX() * 2 + 1);
				packet.putLEShort(loc.getY() * 2 + 1);
			}
		}
		if(flags.get(UpdateFlag.ANIMATION)) {
			packet.putShort(npc.getCurrentAnimation().getId());
			packet.putByteS((byte) npc.getCurrentAnimation().getDelay());
		}
		if(flags.get(UpdateFlag.HIT_2)) {
			packet.putByteS((byte) npc.getDamage().getHitDamage2());
			packet.putByteS((byte) npc.getDamage().getHitType2());
			packet.put((byte) npc.getHealth());
			packet.putByteC(npc.getMaxHealth());
		}

		
	}

}
