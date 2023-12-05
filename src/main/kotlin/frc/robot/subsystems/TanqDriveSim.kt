package frc.robot.subsystems

import com.kauailabs.navx.frc.AHRS
import com.revrobotics.CANSparkMax
import edu.wpi.first.math.controller.DifferentialDriveFeedforward
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj.smartdashboard.Field2d

class TanqDriveSim(
    private val rightLeader: CANSparkMax,
    private val rightFollower: CANSparkMax,
    private val leftLeader: CANSparkMax,
    private val leftFollower: CANSparkMax,
    private val feedForward: DifferentialDriveFeedforward,
    private val gyro: AHRS,
    private val field: Field2d
): TanqDrive(rightLeader, rightFollower, leftLeader, leftFollower, feedForward, gyro) {

    private var leftDist = 0.0
    private var rightDist = 0.0

    var heading = Rotation2d()

    override fun periodic() {
        heading += Rotation2d(desiredSpeed.omegaRadiansPerSecond * (0.020))

        val wheelSpeeds = kinematics.toWheelSpeeds(desiredSpeed)
        leftDist += wheelSpeeds.leftMetersPerSecond * 0.020
        rightDist += wheelSpeeds.rightMetersPerSecond * 0.020

        odometry.update(heading, leftDist, rightDist)

        field.robotPose = getPose()
    }

}