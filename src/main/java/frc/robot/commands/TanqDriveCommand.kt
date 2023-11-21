package frc.robot.commands

import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.CommandBase
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.subsystems.TanqDrive

class TanqDriveCommand(
    private var controller: CommandXboxController,
    private val tanqDrive: TanqDrive
): CommandBase() {

    init {
        addRequirements(tanqDrive)
    }
    override fun execute() {
        var multiple = 5.0

        val arcade = DifferentialDrive.arcadeDriveIK(
            controller.rightTriggerAxis - controller.leftTriggerAxis,
            -controller.leftX,
            true
        )

        val speeds = tanqDrive.kinematics.toChassisSpeeds(
            DifferentialDriveWheelSpeeds(
                arcade.left * multiple,
                arcade.right * multiple
            )
        )

        tanqDrive.setSpeed(speeds)
    }
    override fun isFinished() : Boolean {
        return false
    }
}