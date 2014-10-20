<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
	<property>
		<name>hbase.rootdir</name>
		<value>{$hbase.rootdir}</value>
	</property>
	<property>
		<name>hbase.cluster.distributed</name>
		<value>true</value>
	</property>
	<property>
		<name>hbase.zookeeper.property.clientPort</name>
		<value>2181</value>
	</property>
	<property>
		<name>zookeeper.znode.parent</name>
		<value>{$zookeeper.znode.parent}</value>
	</property>
	<property>
		<name>hbase.zookeeper.quorum</name>
		<value>{$hbase.zookeeper.quorum}</value>
	</property>
	<property>
		<name>hbase.client.scanner.caching</name>
		<value>{$hbase.client.scanner.caching}</value>
	</property>
</configuration>