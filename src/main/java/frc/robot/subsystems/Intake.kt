package frc.robot.subsystems.intake

import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.CommandBase

import frc.robot.subsystems.motor.WrappedMotor

// By making a subsystem a Kotlin object, we ensure there is only ever one instance of it.
// It also reduces the need to have reference variables for the subsystems to be passed around.
object Intake : SubsystemBase() {
    var motor: WrappedMotor
    var piston //Somehow make a piston
    fun createWrappedMotor(id: Int) {
        motor = WrappedMotor(id)
    }
    fun motorVoltage(voltage: Double) {
        //Turn On Motor
        motor.setVoltage(voltage)
    }
    fun stopMotor() {
        motor.stop()
    }
    fun extendPiston() {
        //Piston Extends
    }
    fun retractPiston() {
        //Piston Retracts
    }
}