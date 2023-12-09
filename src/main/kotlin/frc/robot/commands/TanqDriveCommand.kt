package frc.robot.commands

import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.subsystems.TanqDrive

class TanqDriveCommand(
    private var controller: CommandXboxController,
    private val tanqDrive: TanqDrive
): Command() {

    init {
        addRequirements(tanqDrive)
    }
    override fun execute() {
        var multiple = 5.0

        val arcade = DifferentialDrive.arcadeDriveIK(
            controller.rightTriggerAxis - controller.leftTriggerAxis,
            -controller.leftX / 1.1,
            true
        )

//        val speeds = tanqDrive.kinematics.toChassisSpeeds(
//            DifferentialDriveWheelSpeeds(
//                arcade.left * multiple,
//                arcade.right * multiple
//            )
//        )
//
//        tanqDrive.setSpeed(speeds)

        tanqDrive.setSpeedVolts(arcade.left * 8.0, arcade.right * 8.0)
    }
    override fun isFinished() : Boolean {
        return false
    }
}