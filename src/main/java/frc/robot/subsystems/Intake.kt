package frc.robot.subsystems.intake

import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.CommandBase
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//Intake Subsystem Constants
import frc.robot.Constants.IntakeConstants.*

// By making a subsystem a Kotlin object, we ensure there is only ever one instance of it.
// It also reduces the need to have reference variables for the subsystems to be passed around.
object Intake : SubsystemBase() {
    var motor: CANSparkMax = CANSparkMax(INTAKE_MOTOR_ID,MotorType.kBrushLess)
    var piston: DoubleSolenoid = DoubleSolenoid(PneumaticsModuleType.CTREPCM,INTAKE_SOLENOID_FORWARD_CHANNEL,INTAKE_SOLENOID_REVERSE_CHANNEL)
    fun motorVoltage(voltage: Double) {
        return this.runOnce { motor.setVoltage(voltage) }
    }
    fun stopMotor() {
        return this.runOnce { motor.stopMotor() }
    }
    fun extendPiston() {
        return this.runOnce { pision.set(kForward) }
    }
    fun retractPiston() {
        return this.runOnce { pision.set(kReverse) }
    }
}