package frc.robot.subsystems.intake

import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.CommandBase

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// By making a subsystem a Kotlin object, we ensure there is only ever one instance of it.
// It also reduces the need to have reference variables for the subsystems to be passed around.
object Intake : SubsystemBase() {
    var motor: CANSparkMax
    var piston //Somehow make a piston
    fun createMotor(id: Int) {
        return this.runOnce { motor = CANSparkMax(id,MotorType.kBrushLess) }
    }
    fun motorVoltage(voltage: Double) {
        return this.runOnce { motor.setVoltage(voltage) }
    }
    fun stopMotor() {
        return this.runOnce { motor.stopMotor() }
    }
    fun extendPiston() {
        //Piston Extends
    }
    fun retractPiston() {
        //Piston Retracts
    }
}