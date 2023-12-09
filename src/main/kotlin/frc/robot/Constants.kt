package frc.robot

/*
 * The Constants file provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This file should not be used for any other purpose.
 * All String, Boolean, and numeric (Int, Long, Float, Double) constants should use
 * `const` definitions. Other constant types should use `val` definitions.
 */

object Constants
{
    object OperatorConstants
    {
        const val DRIVER_CONTROLLER_PORT = 0
        const val MECH_CONTROLLER_PORT = 1
    }
    object IntakeConstants {
        const val INTAKE_MOTOR_ID = 10
        const val INTAKE_SOLENOID_FORWARD_CHANNEL = 1
        const val INTAKE_SOLENOID_REVERSE_CHANNEL = 2

        const val INTAKE_VOLTAGE = 4.0
    }
    object ConveyorConstants {
        const val CONVEYOR_MOTOR_ID = 2

        const val CONVEYOR_VOLTAGE = 4.0
    }

    object DriveConstants {
        const val TRACKWIDTH = 0.64135
    }
}



