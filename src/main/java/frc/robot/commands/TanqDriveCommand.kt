package frc.robot.commands

import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.Subsystem
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.subsystems.TanqDrive

class TanqDriveCommand(
    private var controller: CommandXboxController,
    private val tanqDrive: TanqDrive
): CommandBase() {

    init {
        addRequirements(tanqDrive)
    }

    override fun initialize() {
    }

    override fun execute() {
        var multiple = 5
        var test = tanqDrive.kinematics.toChassisSpeeds(
            DifferentialDriveWheelSpeeds(
                controller.rightX * multiple,
                controller.rightY * multiple
            )
        )
    }
    override fun isFinished() : Boolean {
        return false
    }
}