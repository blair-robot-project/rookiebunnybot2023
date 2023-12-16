package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.PneumaticsModuleType
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants.IntakeConstants

class Intake(): SubsystemBase() {

    private val motor: CANSparkMax = CANSparkMax(IntakeConstants.INTAKE_MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless)
    private val piston: DoubleSolenoid = DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.INTAKE_SOLENOID_FORWARD_CHANNEL, IntakeConstants.INTAKE_SOLENOID_REVERSE_CHANNEL)

    init {
        motor.inverted = true
        motor.setSmartCurrentLimit(20)
    }
    fun runIntake(): Command {
        return this.runOnce { motor.setVoltage(IntakeConstants.INTAKE_VOLTAGE) }
    }

    fun runIntakeReverse(): Command {
        return this.runOnce { motor.setVoltage(-IntakeConstants.INTAKE_VOLTAGE) }
    }

    fun stopIntake(): Command {
        return this.runOnce{ motor.stopMotor() }
    }

    fun extendPiston(): Command {
        return this.runOnce {
            piston.set(DoubleSolenoid.Value.kForward)
        }
    }

    fun retractPiston(): Command {
        return this.runOnce {
            piston.set(DoubleSolenoid.Value.kReverse)
        }
    }

    companion object {
        fun createIntake(): Intake {
            return Intake()
        }
    }
}