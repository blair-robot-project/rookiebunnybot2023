package frc.robot.commands

import edu.wpi.first.math.filter.SlewRateLimiter
import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.Robot
import frc.robot.subsystems.TanqDrive

class TanqDriveCommand(
    private var controller: CommandXboxController,
    private val tanqDrive: TanqDrive
): Command() {
    val driveLimiter = SlewRateLimiter(1.0)
    val turnLimiter = SlewRateLimiter(0.66)
    init {
        addRequirements(tanqDrive)

    }
    override fun execute() {
        var multiple = 5.0

        val arcade = DifferentialDrive.arcadeDriveIK(
            driveLimiter.calculate(controller.rightTriggerAxis - controller.leftTriggerAxis),
            turnLimiter.calculate(-controller.leftX),
            true,
        )

//        val speeds = tanqDrive.kinematics.toChassisSpeeds(
//            DifferentialDriveWheelSpeeds(
//                arcade.left * multiple,
//                arcade.right * multiple
//            )
//        )
//
//        tanqDrive.setSpeed(speeds)


        tanqDrive.setSpeedVolts(12 * arcade.left, 12 *arcade.right)

    }
    override fun isFinished(): Boolean {
        return false
    }
}