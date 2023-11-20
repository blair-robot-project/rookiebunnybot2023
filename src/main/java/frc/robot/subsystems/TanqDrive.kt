package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import com.revrobotics.SparkMaxPIDController
import edu.wpi.first.math.controller.DifferentialDriveFeedforward
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry
import edu.wpi.first.wpilibj.AnalogGyro
import edu.wpi.first.wpilibj2.command.SubsystemBase


class TanqDrive(
    private val rightLeader: CANSparkMax,
    private val rightFollower: CANSparkMax,
    private val leftLeader: CANSparkMax,
    private val leftFollower: CANSparkMax,
    private val feedForward: DifferentialDriveFeedforward,
    private val gyro: AnalogGyro

) : SubsystemBase() {


    //below should be the width of the robot's track width in inches
    //this is creating a differential drive kinematics object
    //TODO: Edit trackWidthMeters
    val kinematics = DifferentialDriveKinematics(1.0)
    val odometry = DifferentialDriveOdometry(gyro.rotation2d, leftLeader.encoder.position, rightLeader.encoder.position)

    var pose: Pose2d = Pose2d(0.0, 0.0, Rotation2d(0.0))

    var desiredSpeed: ChassisSpeeds = ChassisSpeeds(0.0, 0.0, 0.0)



    fun setSpeed(desiredSpeed : ChassisSpeeds){
        //Here we use the ChassisSpeeds object to

        this.desiredSpeed = desiredSpeed

        val wheelSpeeds = kinematics.toWheelSpeeds(desiredSpeed)
        // Left velocity
        val leftVelocity = wheelSpeeds.leftMetersPerSecond
        // Right velocity
        val rightVelocity = wheelSpeeds.rightMetersPerSecond

        val ffVolts = feedForward.calculate(leftLeader.encoder.velocity, leftVelocity, rightLeader.encoder.velocity, rightVelocity, 0.02)

        leftLeader.pidController.setReference(leftVelocity, CANSparkMax.ControlType.kVelocity, 0,  ffVolts.left, SparkMaxPIDController.ArbFFUnits.kVoltage)

        rightLeader.pidController.setReference(rightVelocity, CANSparkMax.ControlType.kVelocity, 0, ffVolts.right, SparkMaxPIDController.ArbFFUnits.kVoltage)
    }
    fun getPose(): Pose2d{
        return pose
    }

    fun getCurrentSpeeds() : ChassisSpeeds{
        return desiredSpeed
    }
    fun resetPose(){
        odometry.resetPosition(gyro.rotation2d, leftLeader.encoder.position, rightLeader.encoder.position, pose)
    }
    override fun periodic(){
        pose = odometry.update(gyro.rotation2d, leftLeader.encoder.position, rightLeader.encoder.position)

    }




    companion object {
        fun createTanqDrive(arrId: IntArray): TanqDrive {



            val kTrackWidth = 0.381 * 2 // meters
            val kWheelRadius = 0.0508 // meters
            val kEncoderResolution = 4096

            val leftLeader = CANSparkMax(arrId[0], CANSparkMaxLowLevel.MotorType.kBrushless)
            val rightLeader = CANSparkMax(arrId[1], CANSparkMaxLowLevel.MotorType.kBrushless)
            val leftFollower = CANSparkMax(arrId[2], CANSparkMaxLowLevel.MotorType.kBrushless)
            val rightFollower = CANSparkMax(arrId[3], CANSparkMaxLowLevel.MotorType.kBrushless)

            rightLeader.inverted = true

            leftFollower.follow(leftLeader)
            rightFollower.follow(rightFollower)

            val gyro = AnalogGyro(0)

            /*
            Bellow we are converting from motor rotations to meters per second so that we can feed
            these values into the PID. The gearing is a placeholder for the ratio between
            1 full motor spin to 1 wheel spin.
             */
            val gearing = 0.25

            leftLeader.encoder.positionConversionFactor = 2 * Math.PI * kWheelRadius * gearing
            rightLeader.encoder.positionConversionFactor = 2 * Math.PI * kWheelRadius * gearing

            leftLeader.encoder.velocityConversionFactor = 2 * Math.PI * kWheelRadius * gearing / 60
            rightLeader.encoder.velocityConversionFactor =2 * Math.PI * kWheelRadius * gearing / 60


            //create 2 PID controllers for left and right leader motors

            val leftPidController = leftLeader.pidController

            val rightPidController = rightLeader.pidController

            //these values are not set and need to be changed ********
            val kP = 6e-5
            val kI = 0.0
            val kD = 0.0
            val kIz = 0.0
            val kFF = 0.0
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


            //TODO: Change gains
            val feedForward = DifferentialDriveFeedforward(1.0, 3.0, 4.0, 6.0)


            return TanqDrive(rightLeader, rightFollower, leftLeader, leftFollower, feedForward, gyro)
        }
    }
}