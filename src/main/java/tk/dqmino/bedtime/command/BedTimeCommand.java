package tk.dqmino.bedtime.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import tk.dqmino.bedtime.BedTime;
import tk.dqmino.bedtime.Serializer;
import tk.dqmino.bedtime.config.Configuration;

public class BedTimeCommand extends CommandBase {

    private final Configuration config;
    private final Serializer serializer;

    public BedTimeCommand(Configuration config, Serializer serializer) {
        this.config = config;
        this.serializer = serializer;
    }

    @Override
    public String getCommandName() {
        return "bedtime";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/bedtime <setting> <value>";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelpMessage(sender);
            return;
        }
        String setting = args[0];

        if (args.length < 2) {
            // no value
            if (args[0].equalsIgnoreCase("on")) {
                config.setEnable(true);
                serializer.serialize();
                sender.addChatMessage(new ChatComponentText(BedTime.PREFIX + EnumChatFormatting.GREEN
                        + "Enabled Bed Timer"));
                return;
            }
            if (args[0].equalsIgnoreCase("off")) {
                config.setEnable(false);
                serializer.serialize();
                sender.addChatMessage(new ChatComponentText(BedTime.PREFIX + EnumChatFormatting.GREEN
                        + "Disabled Bed Timer"));
                return;
            }
            sendErrorMessage(sender);
            return;
        }

        String valueToSet = args[1];

        try {
            switch (setting) {
                case "x":
                    config.setX(Integer.parseInt(valueToSet));
                    serializer.serialize();
                    sender.addChatMessage(new ChatComponentText(BedTime.PREFIX + EnumChatFormatting.GREEN
                            + "Set X Coordinate to " + valueToSet));
                    break;
                case "y":

                    config.setY(Integer.parseInt(valueToSet));
                    serializer.serialize();
                    sender.addChatMessage(new ChatComponentText(BedTime.PREFIX + EnumChatFormatting.GREEN
                            + "Set Y Coordinate to " + valueToSet));
                    break;
                case "size":
                    config.setSize(Double.parseDouble(valueToSet));
                    sender.addChatMessage(new ChatComponentText(BedTime.PREFIX + EnumChatFormatting.GREEN
                            + "Set Font Size to " + valueToSet));
                    break;
                default:
                    sendErrorMessage(sender);
                    break;
            }

        } catch (Exception exception) {
            sendErrorMessage(sender);
            exception.printStackTrace();
        }

    }

    private void sendHelpMessage(ICommandSender sender) {
        sender.addChatMessage(new ChatComponentText(BedTime.PREFIX + EnumChatFormatting.BLUE
                + "Commands: \n - /bedtime <x / y / size> <value>\n - /bedtime <on/off>"));
    }

    private void sendErrorMessage(ICommandSender sender) {
        sender.addChatMessage(new ChatComponentText(BedTime.PREFIX + EnumChatFormatting.DARK_RED
                + "An Error Occured. Please check your arguments, if you're unsure please use /bedtime help or " +
                "/bedtime"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED
                + getCommandUsage(sender)));
    }

}