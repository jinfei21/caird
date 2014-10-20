<?xml version="1.0" encoding="UTF-8"?>
<configuration>

	<!--Output to central logging -->
	<appender name="C"
		class="com.ctrip.freeway.appender.CentralLoggingAppender">
		<appId>920821</appId>
		<serverIp>{$cloggingServerIp}</serverIp>
		<serverPort>63100</serverPort>
	</appender>

	<root level="INFO">
		<appender-ref ref="C" />
	</root>

</configuration>