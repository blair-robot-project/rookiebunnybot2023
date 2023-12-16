package frc.robot

import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import edu.wpi.first.wpilibj2.command.button.Trigger
import frc.robot.Constants.OperatorConstants
import frc.robot.commands.Autos
import edu.wpi.first.wpilibj.smartdashboard.Field2d
import frc.robot.commands.Characterization
import frc.robot.subsystems.Conveyor
import frc.robot.subsystems.Intake
import frc.robot.subsystems.TanqDrive.Companion.createTanqDrive

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the [Robot]
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 *
 * In Kotlin, it is recommended that all your Subsystems are Kotlin objects. As such, there
 * can only ever be a single instance. This eliminates the need to create reference variables
 * to the various subsystems in this container to pass into to commands. The commands can just
 * directly reference the (single instance of the) object.
 */
object RobotContainer
{
    val field = Field2d()

    // Replace with CommandPS4Controller or CommandJoystick if needed
    val driveController = CommandXboxController(OperatorConstants.DRIVER_CONTROLLER_PORT)
    val mechController = CommandXboxController(OperatorConstants.MECH_CONTROLLER_PORT)

    val drive = createTanqDrive(intArrayOf(6, 7, 3, 4), field)

    // intake and conveyor have gearings of 3:1 and 5:1, respectively, but are not accounted for
    // because encoder values are not measured

    val intake = Intake.createIntake()
    val conveyor = Conveyor.createConveyor()

    init
    {
        configureBindings()
        // Reference the Autos object so that it is initialized, placing the chooser on the dashboard
        Autos
    }

    /**
     * Use this method to define your `trigger->command` mappings. Triggers can be created via the
     * [Trigger] constructor that takes a [BooleanSupplier][java.util.function.BooleanSupplier]
     * with an arbitrary predicate, or via the named factories in [GenericHID][edu.wpi.first.wpilibj2.command.button.CommandGenericHID]
     * subclasses such for [Xbox][CommandXboxController]/[PS4][edu.wpi.first.wpilibj2.command.button.CommandPS4Controller]
     * controllers or [Flight joysticks][edu.wpi.first.wpilibj2.command.button.CommandJoystick].
     */
    private fun configureBindings()
    {
        mechController.a().onTrue(
            intake.runIntake()
        ).toggleOnFalse(
            intake.stopIntake()
        )

        mechController.b().onTrue(
            intake.runIntakeReverse()
        ).toggleOnFalse(
            intake.stopIntake()
        )


        mechController.rightBumper().onTrue(
            intake.extendPiston()
        )
        mechController.leftBumper().onTrue(
            intake.retractPiston()
        )

        mechController.x().onTrue(
            conveyor.startConveyor()
        ).onFalse(
            conveyor.stopConveyor()
        )


        driveController.start().toggleOnTrue(Characterization(
            this.drive,
            false,
            "Tank Drive",
            this.drive::setSpeedVolts,
            this.drive::getDriveVel
        ))
    }
}