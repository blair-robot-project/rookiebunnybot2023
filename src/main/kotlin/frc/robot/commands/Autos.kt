package frc.robot.commands

import com.pathplanner.lib.auto.AutoBuilder
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.PrintCommand
import com.pathplanner.lib.auto.NamedCommands
import com.pathplanner.lib.commands.PathPlannerAuto
import com.pathplanner.lib.util.ReplanningConfig
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.RobotContainer

object Autos
{

    init{
        NamedCommands.registerCommand("move", InstantCommand())
        NamedCommands.registerCommand("ScoreLow", SequentialCommandGroup())
        NamedCommands.registerCommand("GroundPickup", SequentialCommandGroup())
    }


    private val autoModeChooser = SendableChooser<AutoMode>().apply {
        AutoMode.values().forEach { addOption(it.optionName, it) }
        setDefaultOption(AutoMode.default.optionName, AutoMode.default)
    }

    val defaultAutonomousCommand: Command
        get() = AutoMode.default.command

    val selectedAutonomousCommand: Command
        get() = autoModeChooser.selected?.command ?: defaultAutonomousCommand

    /** Example static factory for an autonomous command.  */
    private fun exampleAuto(): Command {
        AutoBuilder.configureRamsete(
            RobotContainer.TanqDrive::getPose, // Robot pose supplier
            RobotContainer.TanqDrive::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
            RobotContainer.TanqDrive::getCurrentSpeeds, // Current ChassisSpeeds supplier
            RobotContainer.TanqDrive::setSpeed, // Method that will drive the robot given ChassisSpeeds
            ReplanningConfig(), // Default path replanning config. See the API for the options here
            RobotContainer.TanqDrive // Reference to this subsystem to set requirements
        )

        return PathPlannerAuto("1ScorePickup")
    }
    private fun autoMovement(): Command {
        AutoBuilder.configureRamsete(
            RobotContainer.TanqDrive::getPose, // Robot pose supplier
            RobotContainer.TanqDrive::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
            RobotContainer.TanqDrive::getCurrentSpeeds, // Current ChassisSpeeds supplier
            RobotContainer.TanqDrive::setSpeed, // Method that will drive the robot given ChassisSpeeds
            ReplanningConfig(), // Default path replanning config. See the API for the options here
            RobotContainer.TanqDrive // Reference to this subsystem to set requirements
        )

        return PathPlannerAuto("RobotAuto")
    }


    private fun exampleAuto2() = PrintCommand("An example Auto Mode that just prints a value")

    /**
     * An enumeration of the available autonomous modes. It provides an easy
     * way to manage all our autonomous modes. The [autoModeChooser] iterates
     * over its values, adding each value to the chooser.
     *
     * @param optionName The name for the [autoModeChooser] option.
     * @param command The [Command] to run for this mode.
     */
    @Suppress("unused")
    private enum class AutoMode(val optionName: String, val command: Command)
    {
        // TODO: Replace with real auto modes and their corresponding commands
        CUSTOM_AUTO_1("Custom Auto Mode 1", exampleAuto()),
        CUSTOM_AUTO_2("Custom Auto Mode 2", exampleAuto2()),
        CUSTOM_AUTO_3("Custom Auto Mode 3", ExampleCommand()),
        ;

        companion object
        {
            /** The default auto mode. */
            val default = CUSTOM_AUTO_1
        }
    }
}