package frc.robot.commands

import edu.wpi.first.math.MathUtil
import edu.wpi.first.math.filter.SlewRateLimiter
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.subsystems.TanqDrive

class TanqDriveCommand(
    private var controller: CommandXboxController,
    private val tanqDrive: TanqDrive
): Command() {
    private val driveLimiter = SlewRateLimiter(1.0, -1.0, 0.0)
    private val turnLimiter = SlewRateLimiter(1.0, -1.0, 0.0)
    init {
        addRequirements(tanqDrive)

    }
    override fun execute() {

        val arcade = DifferentialDrive.arcadeDriveIK(
            driveLimiter.calculate(controller.rightTriggerAxis - controller.leftTriggerAxis),
            turnLimiter.calculate(MathUtil.applyDeadband(-controller.leftX, 0.05)),
            true,
        )

//        val speeds = tanqDrive.kinematics.toChassisSpeeds(
//            DifferentialDriveWheelSpeeds(
//                arcade.left * Constants.DriveConstants.MAX_LINEAR_SPEED,
//                arcade.right * Constants.DriveConstants.MAX_LINEAR_SPEED
//            )
//        )
//
//        println(speeds)
//
//        tanqDrive.setSpeed(speeds)

        tanqDrive.setSpeedVolts(12 * arcade.left, 12 *arcade.right)

    }
    override fun isFinished(): Boolean {
        return false
    }
}