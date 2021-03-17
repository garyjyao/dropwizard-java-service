package dropwizard.java.example;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.dropwizard.ArmeriaBundle;
import com.linecorp.armeria.server.ServerBuilder;
import dropwizard.java.example.filter.DiagnosticContextFilter;
import dropwizard.java.example.healthcheck.DefaultHealthCheck;
import dropwizard.java.example.resource.RootResource;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ExampleApp extends io.dropwizard.Application<ExampleAppConfig> {

    public static void main(String[] args) throws Exception {
        new ExampleApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<ExampleAppConfig> bootstrap) {
        final ArmeriaBundle<ExampleAppConfig> bundle =
                new ArmeriaBundle<ExampleAppConfig>() {
                    @Override
                    public void configure(ServerBuilder builder) {
                        builder.service("/", (ctx, res) -> HttpResponse.of(MediaType.HTML_UTF_8, "<h2>It works!</h2>"));
                        builder.service("/armeria", (ctx, res) -> HttpResponse.of("Hello, Armeria!"));

                        // builder.annotatedService(new HelloService());

                        // You can also bind asynchronous RPC services such as Thrift and gRPC:
                        // builder.service(THttpService.of(...));
                        // builder.service(GrpcService.builder()...build());
                    }
                };
        bootstrap.addBundle(bundle);
    }

    @Override
    public void run(ExampleAppConfig config, Environment env) {
        env.jersey().register(new RootResource(config.getAppName()));
        env.jersey().register(new DiagnosticContextFilter());
        env.healthChecks().register("default", new DefaultHealthCheck());
    }
}
