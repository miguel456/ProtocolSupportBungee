package protocolsupport.protocol.pipeline.version;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ReferenceCountUtil;
import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.MinecraftEncoder;
import net.md_5.bungee.protocol.Protocol;
import protocolsupport.protocol.packet.middle.WriteableMiddlePacket;
import protocolsupport.protocol.utils.registry.ClassMapMiddleTransformerRegistry;

public abstract class PacketEncoder extends MinecraftEncoder {

	protected final ClassMapMiddleTransformerRegistry<DefinedPacket, WriteableMiddlePacket<?>> registry = new ClassMapMiddleTransformerRegistry<>();

	public PacketEncoder(Protocol protocol, boolean server, int protocolVersion) {
		super(protocol, server, protocolVersion);
	}

	@Override
	public void write(final ChannelHandlerContext ctx, final Object msgObject, final ChannelPromise promise) throws Exception {
		try {
			if (acceptOutboundMessage(msgObject)) {
				DefinedPacket msg = (DefinedPacket) msgObject;
				try {
					encode(ctx, msg, null);
				} finally {
					ReferenceCountUtil.release(msg);
				}
			} else {
				ctx.write(msgObject, promise);
			}
		} catch (EncoderException e) {
			throw e;
		} catch (Throwable e2) {
			throw new EncoderException(e2);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void encode(ChannelHandlerContext ctx, DefinedPacket msg, ByteBuf out) throws Exception {
		WriteableMiddlePacket<DefinedPacket> transformer = (WriteableMiddlePacket<DefinedPacket>) registry.getTransformer(msg.getClass());
		transformer.toData(msg).forEach(ctx::writeAndFlush);
	}

}
