package protocolsupport.protocol.pipeline;

import java.util.EnumMap;

import io.netty.channel.Channel;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;

public abstract class IPipeLineBuilder {

	//TODO: a kick build for LEGACY versions
	public static final EnumMap<ProtocolVersion, IPipeLineBuilder> BUILDERS = new EnumMap<ProtocolVersion, IPipeLineBuilder>(ProtocolVersion.class);
	static {
		protocolsupport.protocol.pipeline.version.v_1_6.PipeLineBuilder v6builder = new protocolsupport.protocol.pipeline.version.v_1_6.PipeLineBuilder();
		BUILDERS.put(ProtocolVersion.MINECRAFT_1_6_4, v6builder);
		BUILDERS.put(ProtocolVersion.MINECRAFT_1_6_2, v6builder);
		BUILDERS.put(ProtocolVersion.MINECRAFT_1_6_1, v6builder);
	}

	public abstract void buildBungeeClient(Channel channel, Connection connection);

	public abstract void buildBungeeServer(Channel channel, Connection connection);

}
