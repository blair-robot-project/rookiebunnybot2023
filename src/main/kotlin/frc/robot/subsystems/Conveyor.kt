package frc.robot.subsystems

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants.ConveyorConstants

class Conveyor(): SubsystemBase() {

    private val motor: CANSparkMax = CANSparkMax(ConveyorConstants.CONVEYOR_MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless)

    init {
        motor.setSmartCurrentLimit(20)
    }

    fun startConveyor(): Command {
        return this.runOnce { motor.setVoltage(ConveyorConstants.CONVEYOR_VOLTAGE)}
    }

    fun stopConveyor(): Command {
        return this.runOnce { motor.stopMotor() }
    }

    companion object {
        fun createConveyor(): Conveyor {
            return Conveyor()
        }
    }
}
