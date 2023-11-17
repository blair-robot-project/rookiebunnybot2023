package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.SubsystemBase


class TanqDrive(
    private val rightLeader: CANSparkMax,
    private val rightFollower: CANSparkMax,
    private val leftLeader: CANSparkMax,
    private val leftFollower: CANSparkMax
) : SubsystemBase() {

    private val leftEncoder = leftLeader.encoder
    private val rightEncoder = rightLeader.encoder

    //below should be the width of the robot's track width in inches
    //this is creating a differential drive kinematics object
    var kinematics = DifferentialDriveKinematics(1.0)




    fun setSpeed(desiredSpeed : ChassisSpeeds){
        //Here we use the ChassisSpeeds object to

        val wheelSpeeds = kinematics.toWheelSpeeds(desiredSpeed)
        // Left velocity
        val leftVelocity = wheelSpeeds.leftMetersPerSecond
        // Right velocity
        val rightVelocity = wheelSpeeds.rightMetersPerSecond








    }


    companion object {
        fun createTanqDrive(arrId: IntArray): TanqDrive {

            val leftLeader = CANSparkMax(arrId[0], CANSparkMaxLowLevel.MotorType.kBrushless)
            val rightLeader = CANSparkMax(arrId[1], CANSparkMaxLowLevel.MotorType.kBrushless)
            val leftFollower = CANSparkMax(arrId[2], CANSparkMaxLowLevel.MotorType.kBrushless)
            val rightFollower = CANSparkMax(arrId[3], CANSparkMaxLowLevel.MotorType.kBrushless)

            leftFollower.follow(leftLeader)
            rightFollower.follow(rightFollower)


            //create 2 PID controllers for left and right leader motors

            val leftPidController = leftLeader.pidController

            val rightPidController = rightLeader.pidController

            //below are 2 sets of PID coefficients, one for the left pid motor and for the right one
            //these values are not set and need to be changed ********
            val leftkP = 6e-5;
            val leftkI = 0.0;
            val leftkD = 0.0;
            val leftkIz = 0.0;
            val leftkFF = 0.000015;
            val leftkMaxOutput = 1.0;
            val leftkMinOutput = -1.0;
            val leftmaxRPM = 5700;

            val rightkP = 6e-5;
            val rightkI = 0.0;
            val rightkD = 0.0;
            val rightkIz = 0.0;
            val rightkFF = 0.000015;
            val rightkMaxOutput = 1.0;
            val rightkMinOutput = -1.0;
            val rightmaxRPM = 5700;

            //setting pid values for left and right PID Controllers
            leftPidController.setP(leftkP);
            leftPidController.setI(leftkI);
            leftPidController.setD(leftkD);
            leftPidController.setIZone(leftkIz);
            leftPidController.setFF(leftkFF);
            leftPidController.setOutputRange(leftkMinOutput, leftkMaxOutput);

            rightPidController.setP(rightkP);
            rightPidController.setI(rightkI);
            rightPidController.setD(rightkD);
            rightPidController.setIZone(rightkIz);
            rightPidController.setFF(rightkFF);
            rightPidController.setOutputRange(rightkMinOutput, rightkMaxOutput);





            return TanqDrive(rightLeader, rightFollower, leftLeader, leftFollower)
        }
    }
}