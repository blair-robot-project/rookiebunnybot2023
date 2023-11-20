package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import edu.wpi.first.math.controller.DifferentialDriveFeedforward
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj.AnalogGyro
import edu.wpi.first.wpilibj.Timer.getFPGATimestamp
import edu.wpi.first.wpilibj.smartdashboard.Field2d

class TanqDriveSim(
    private val rightLeader: CANSparkMax,
    private val rightFollower: CANSparkMax,
    private val leftLeader: CANSparkMax,
    private val leftFollower: CANSparkMax,
    private val feedForward: DifferentialDriveFeedforward,
    private val gyro: AnalogGyro,
    private val field: Field2d
): TanqDrive(rightLeader, rightFollower, leftLeader, leftFollower, feedForward, gyro) {

    private var lastTime = getFPGATimestamp()

    override fun periodic() {
        val currTime = getFPGATimestamp()
        var heading = super.getGyro().rotation2d.plus(Rotation2d(super.desiredSpeed.omegaRadiansPerSecond * (currTime - lastTime)))
        this.lastTime = currTime

        setSpeed(super.desiredSpeed)

        field.setRobotPose(super.getPose())

        super.periodic()
    }

}