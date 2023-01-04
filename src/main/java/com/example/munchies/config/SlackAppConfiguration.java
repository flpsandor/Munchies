package com.example.munchies.config;

import com.example.munchies.service.SlackService;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.block.LayoutBlock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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

    @Bean
    public App initSlackApp(AppConfig config) {
        App app = new App(config);
        if (config.getClientId() != null) {
            app.asOAuthApp(true);
        }
        app.command("/echo", (req, ctx) -> ctx.ack(r -> r.text("Hello from slack bot")));
        app.command("/restaurants", (req, ctx) -> {
            List<LayoutBlock> restaurants = slackService.findAllForSlack();
            return ctx.ack(r -> r.blocks(restaurants));
        });
        app.command("/order_new", (req, ctx) -> {
            String[] values = req.getPayload().getText().split(" ");
            String restaurantShortName = values[0];
            Integer timeout = Integer.parseInt(values[1]);
            String employee = req.getPayload().getUserName();
            List<LayoutBlock> newGroupOrder = slackService.createGroupOrderFromSlack(employee, restaurantShortName, timeout);
            return ctx.ack(r -> r.blocks(newGroupOrder));
        });
        app.command("/order", (req, ctx) -> {
            String[] values = req.getPayload().getText().split(" ");
            Long orderId = Long.parseLong(values[0]);
            String itemDescription = values[2];
            Double itemPrice = Double.parseDouble(values[3]);
            String employee = req.getPayload().getUserName();
            List<LayoutBlock> item = slackService.createOrderItemFromSlack(orderId, employee, itemDescription, itemPrice);
            return ctx.ack(r->r.blocks(item));
        });
        app.command("/order_info", (req, ctx) -> {
            Long id = Long.valueOf(req.getPayload().getText());
            List<LayoutBlock> orderInfo = slackService.findAllByGroupIdFromSlack(id);
            return ctx.ack(orderInfo);
        });

        return app;
    }

}
