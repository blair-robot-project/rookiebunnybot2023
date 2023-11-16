package frc.robot.subsystems.conveyor

import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.CommandBase

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//Conveyor Subsystem Constants
import frc.robot.Constants.ConveryorConstants.*

// By making a subsystem a Kotlin object, we ensure there is only ever one instance of it.
// It also reduces the need to have reference variables for the subsystems to be passed around.
object Conveyor : SubsystemBase() {
    var motor: CANSparkMax = CANSparkMax(CONVEYOR_MOTOR_ID,MotorType.kBrushLess)
    fun motorVoltage(voltage: Double): Command {
        return this.runOnce { motor.setVoltage(voltage) }
    }
    fun stopMotor() {
        return this.runOnce { motor.stopMotor() }
    }
}
