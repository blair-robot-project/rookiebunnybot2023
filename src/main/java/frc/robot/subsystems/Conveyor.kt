package frc.robot.subsystems.coveryor

import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.CommandBase

import frc.robot.subsystems.motor.WrappedMotor

// By making a subsystem a Kotlin object, we ensure there is only ever one instance of it.
// It also reduces the need to have reference variables for the subsystems to be passed around.
object Conveyor : SubsystemBase() {
    var motor: WrappedMotor
    fun createWrappedMotor(id: Int) {
        motor = WrappedMotor(id)
    }
    fun motorVoltage(voltage: Double) {
        motor.setVoltage(voltage)
    }
    fun stopMotor() {
        motor.stop()
    }
}