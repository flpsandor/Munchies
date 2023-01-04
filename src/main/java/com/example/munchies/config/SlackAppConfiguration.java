package com.example.munchies.config;

import com.example.munchies.service.SlackService;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
public class SlackAppConfiguration {

    private final SlackService slackService;

    public SlackAppConfiguration(SlackService slackService) {
        this.slackService = slackService;
    }

    @Bean
    public AppConfig loadSingleWorkspaceAppConfig() {
        return AppConfig.builder()
                .singleTeamBotToken(System.getenv("SLACK_BOT_TOKEN"))
                .signingSecret(System.getenv("SLACK_SIGNING_SECRET"))
                .build();
    }

    public AppConfig loadOAuthConfig() {
        return AppConfig.builder()
                .singleTeamBotToken(null)
                .clientId(System.getenv("SLACK_CLIENT_ID"))
                .clientSecret(System.getenv("SLACK_CLIENT_SECRET"))
                .signingSecret(System.getenv("SLACK_SIGNING_SECRET"))
                .scope("app_mentions:read,channels:history,channels:read,chat:write")
                .oauthInstallPath("/slack/install")
                .oauthRedirectUriPath("/slack/oauth_redirect")
                .build();
    }

    @Bean
    public App initSlackApp(AppConfig config) {
        App app = new App(config);
        if (config.getClientId() != null) {
            app.asOAuthApp(true);
        }
        app.command("/echo", (req, ctx) -> ctx.ack(r -> r.text("Hello from slack bot")));
        app.command("/restaurants", (req, ctx) -> {
            return ctx.ack(r -> r.text(slackService.findAllForSlack()
                    .stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining("\n"))));
        });
        app.command("/order_new", (req, ctx) -> {
            String[] values = req.getPayload().getText().split(" ");
            String restaurantShortName = values[0];
            Integer timeout = Integer.parseInt(values[1]);
            String employee = req.getPayload().getUserName();
            return ctx.ack(slackService.createGroupOrderFromSlack(employee, restaurantShortName, timeout).toString());
        });
        app.command("/order", (req, ctx) -> {
            String[] values = req.getPayload().getText().split(" ");
            Long orderId = Long.parseLong(values[0]);
            String itemDescription = values[2];
            Double itemPrice = Double.parseDouble(values[3]);
            String employee = req.getPayload().getUserName();
            return ctx.ack(String.valueOf(slackService.createOrderItemFromSlack(orderId, employee, itemDescription, itemPrice)));
        });
        app.command("/order_info", (req, ctx) -> {
            Long id = Long.valueOf(req.getPayload().getText());
            return ctx.ack(slackService.findAllByGroupIdFromSlack(id).toString());
        });

        return app;
    }

}
