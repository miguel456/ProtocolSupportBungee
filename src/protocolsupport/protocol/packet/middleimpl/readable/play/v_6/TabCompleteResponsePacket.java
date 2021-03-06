package protocolsupport.protocol.packet.middleimpl.readable.play.v_6;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.md_5.bungee.protocol.PacketWrapper;
import net.md_5.bungee.protocol.packet.TabCompleteResponse;
import protocolsupport.protocol.packet.middleimpl.readable.DefinedReadableMiddlePacket;
import protocolsupport.protocol.serializer.LegacySerializer;

public class TabCompleteResponsePacket extends DefinedReadableMiddlePacket {

	public static final int PACKET_ID = 0xCB;

	public TabCompleteResponsePacket() {
		super(PACKET_ID);
	}

	protected String string;

	@Override
	protected void read0(ByteBuf from) {
		string = LegacySerializer.readString(from);
	}

	@Override
	public Collection<PacketWrapper> toNative() {
		return Collections.singletonList(new PacketWrapper(new TabCompleteResponse(Arrays.asList(string.split("\u0000"))), Unpooled.wrappedBuffer(readbytes)));
	}

}
