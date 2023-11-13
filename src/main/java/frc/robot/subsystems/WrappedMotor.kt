package frc.robot.subsystems.motor

import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.CommandBase

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
class WrappedMotor(id: Int) : SubsystemBase() {
    val motor: CANSparkMax = CANSparkMax(id,MotorType.kBrushless)
    fun setVoltage(voltage: Double) {
        motor.setVoltage(voltage)
    }
    fun stop() {
        motor.stopMotor()
    }
}