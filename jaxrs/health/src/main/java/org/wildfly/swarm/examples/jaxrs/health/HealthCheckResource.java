package org.wildfly.swarm.examples.jaxrs.health;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.wildfly.swarm.monitor.Health;
import org.wildfly.swarm.monitor.HealthStatus;

@Path("/app")
public class HealthCheckResource {

    @GET
    @Path("/health")
    @Health
    public HealthStatus checkDiskspace() {
        File path = new File(".");
        long freeBytes = path.getFreeSpace();
        long threshold = 1024 * 1024 * 100; // 100mb
        return freeBytes>threshold ? HealthStatus.up() : HealthStatus.down().withAttribute("freebytes", freeBytes);
    }

    @GET
    @Path("/second-health")
    @Health(inheritSecurity = false)
    public HealthStatus checkSomethingElse() {
        return HealthStatus.up();
    }

}
