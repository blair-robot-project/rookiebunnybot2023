package frc.robot.subsystems

import com.kauailabs.navx.frc.AHRS
import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.math.controller.DifferentialDriveFeedforward
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.smartdashboard.Field2d
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants


open class TanqDrive(
    private val rightLeader: CANSparkMax,
    private val leftLeader: CANSparkMax,
    private val feedForward: DifferentialDriveFeedforward,
    private val gyro: AHRS
) : SubsystemBase() {

    //below should be the width of the robot's track width in inches
    //this is creating a differential drive kinematics object
    //TODO: Edit trackWidthMeters

    val kinematics = DifferentialDriveKinematics(Constants.DriveConstants.TRACKWIDTH)

    val odometry = DifferentialDriveOdometry(
        Rotation2d.fromDegrees(gyro.fusedHeading.toDouble()),
        leftLeader.encoder.position,
        rightLeader.encoder.position
    )

    var desiredSpeed: ChassisSpeeds = ChassisSpeeds(0.0, 0.0, 0.0)


    fun getGyroRotation(): Rotation2d {
        return Rotation2d.fromDegrees(gyro.fusedHeading.toDouble())
    }

    fun setSpeed(desiredSpeed : ChassisSpeeds) {
        //Here we use the ChassisSpeeds object to

        this.desiredSpeed = desiredSpeed

        val wheelSpeeds = kinematics.toWheelSpeeds(desiredSpeed)

        println(wheelSpeeds)

        wheelSpeeds.desaturate(Constants.DriveConstants.MAX_LINEAR_SPEED)
        // Left velocity
        val leftVelocity = wheelSpeeds.leftMetersPerSecond
        // Right velocity
        val rightVelocity = wheelSpeeds.rightMetersPerSecond

        val ffVolts = feedForward.calculate(leftLeader.encoder.velocity, leftVelocity, rightLeader.encoder.velocity, rightVelocity, 0.02)

        leftLeader.setVoltage(ffVolts.left)
        rightLeader.setVoltage(ffVolts.right)

        //leftLeader.pidController.setReference(leftVelocity, CANSparkMax.ControlType.kVelocity, 0,  ffVolts.left, SparkMaxPIDController.ArbFFUnits.kVoltage)

        //rightLeader.pidController.setReference(rightVelocity, CANSparkMax.ControlType.kVelocity, 0, ffVolts.right, SparkMaxPIDController.ArbFFUnits.kVoltage)
    }

    fun setSpeedVolts(voltageLeft: Double, voltageRight: Double) {
        leftLeader.setVoltage(voltageLeft)
        rightLeader.setVoltage(voltageRight)
    }

    fun setSpeedVolts(voltage: Double) {
        leftLeader.setVoltage(voltage)
        rightLeader.setVoltage(voltage)
    }

    fun getDriveVel(): Double {
        return (leftLeader.encoder.velocity + rightLeader.encoder.velocity) / 2
    }

    fun getPose(): Pose2d {
        return odometry.poseMeters
    }

    fun getCurrentSpeeds(): ChassisSpeeds {
        return desiredSpeed
    }
    fun resetPose(pose: Pose2d) {
        odometry.resetPosition(getGyroRotation(), leftLeader.encoder.position, rightLeader.encoder.position, pose)
    }
    override fun periodic() {
        odometry.update(getGyroRotation(), leftLeader.encoder.position, rightLeader.encoder.position)
    }

    companion object {
        fun createTanqDrive(arrId: IntArray, field: Field2d): TanqDrive {
            val kWheelRadius = 0.0508 // meters

            val leftLeader = CANSparkMax(arrId[0], CANSparkMaxLowLevel.MotorType.kBrushless)
            val rightLeader = CANSparkMax(arrId[1], CANSparkMaxLowLevel.MotorType.kBrushless)
            val leftFollower = CANSparkMax(arrId[2], CANSparkMaxLowLevel.MotorType.kBrushless)
            val rightFollower = CANSparkMax(arrId[3], CANSparkMaxLowLevel.MotorType.kBrushless)

            leftLeader.setSmartCurrentLimit(40)
            rightLeader.setSmartCurrentLimit(40)
            leftFollower.setSmartCurrentLimit(40)
            rightFollower.setSmartCurrentLimit(40)

            rightLeader.inverted = true
            leftLeader.inverted = false

            leftFollower.follow(leftLeader, false)
            rightFollower.follow(rightLeader, false)

            val gyro = AHRS(SPI.Port.kMXP)

            /*
            Bellow we are converting from motor rotations to meters per second so that we can feed
            these values into the PID. The gearing is a placeholder for the ratio between
            1 full motor spin to 1 wheel spin.
             */
            val gearing = 1 / 5.86

            leftLeader.encoder.positionConversionFactor = 2 * Math.PI * kWheelRadius * gearing
            rightLeader.encoder.positionConversionFactor = 2 * Math.PI * kWheelRadius * gearing

            leftLeader.encoder.velocityConversionFactor = 2 * Math.PI * kWheelRadius * gearing / 60
            rightLeader.encoder.velocityConversionFactor =2 * Math.PI * kWheelRadius * gearing / 60


            //create 2 PID controllers for left and right leader motors

            val leftPidController = leftLeader.pidController

            val rightPidController = rightLeader.pidController

            //these values are not set and need to be changed ********
            val kP = 0.0005
            val kI = 0.005
            val kD = 0.0
            val kIz = 0.0
            val kFF = 0.75
            val kMaxOutput = 1.0
            val kMinOutput = -1.0
            val maxRPM = 5700

            //setting pid values for left and right PID Controllers
            leftPidController.setP(kP)
            leftPidController.setI(kI)
            leftPidController.setD(kD)
            leftPidController.setIZone(kIz)
            leftPidController.setFF(kFF)
            leftPidController.setOutputRange(kMinOutput, kMaxOutput)

            rightPidController.setP(kP)
            rightPidController.setI(kI)
            rightPidController.setD(kD)
            rightPidController.setIZone(kIz)
            rightPidController.setFF(kFF)
            rightPidController.setOutputRange(kMinOutput, kMaxOutput)

            leftLeader.burnFlash()
            rightLeader.burnFlash()
            leftFollower.burnFlash()
            rightFollower.burnFlash()


            //TODO: Change gains
            val feedForward = DifferentialDriveFeedforward(
                Constants.DriveConstants.kV,
                1.0,
                Constants.DriveConstants.kV,
                1.0)

            if (RobotBase.isReal()) {
                return TanqDrive(rightLeader, leftLeader, feedForward, gyro)
            }
            return TanqDriveSim(rightLeader, rightFollower, leftLeader, leftFollower, feedForward, gyro, field)
        }
    }
}