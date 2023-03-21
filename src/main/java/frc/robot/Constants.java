package frc.robot;

import java.util.Arrays;
import java.util.List;

public class Constants {
    
    // Controller Constants.
    public static final class ControllerConstants {

        public static final int LEFT_JOYSTICK = 0;
        public static final int RIGHT_JOYSTICK = 1;
        public static final double JOYSTICK_DEADBAND = 0.05;

        public static final int DRIVER_TWO = 2;
    }

    // Autonomous Constants.
    public static final class AutonomousConstants {

        public static final double MAX_VELOCITY = 3.81;
        public static final double MAX_ACCELERATION = 3.0;

        public static final double GYRO_STRAIGHT_KP = 0.005;
        public static final double GYRO_TURNING_KP = 0.02;

        public static final double TURNING_ANGLE_TOLERANCE = 5.0;
        public static final double TURNUNG_ANGLE_TURN_RATE_TOLERANCE = 10.0;
        public static final double TURNING_ANGLE_KF = 0.2;
    }

    // West Coast Constants.
    public static final class WestCoastConstants {

        public static final double WHEEL_DIAMETER = 0.1524;
        public static final double MOTOR_GEAR_RATIO = 8.68;
        public static final double TRACK_WIDTH = 0.6223;

        public static final int BACK_LEFT_MOTOR = 1;
        public static final int CENTER_LEFT_MOTOR = 2;
        public static final int FRONT_LEFT_MOTOR = 3;

        public static final int BACK_RIGHT_MOTOR = 4;
        public static final int CENTER_RIGHT_MOTOR = 5;
        public static final int FRONT_RIGHT_MOTOR = 6;
    }

    // Shooter Constants.
    public static final class CubertConstants {

        public static final int LEFT_INTAKE_PISTON_FORWARD = 14; 
        public static final int LEFT_INTAKE_PISTON_REVERSE = 4; 
        public static final int RIGHT_INTAKE_PISTON_FORWARD = 15; 
        public static final int RIGHT_INTAKE_PISTON_REVERSE = 6; 

        public static final int INTAKE_ROLLER_MOTOR = 20; 
        public static final int INDEXER_MOTOR = 17; 
        public static final int SHOOTER_MOTOR = 10;

        public enum SHOTS {
            LEFT(0),
            RIGHT(5000),
            UP(7000),
            DOWN(3000);

            public final int velocity;
            private SHOTS (int velocity) { this.velocity = velocity; }
        }
    }
    
    // Manipulator Constants.
    public static final class ManipulatorConstants {

        public enum ARM_POSITIONS {
            PLATFORM(123000, 350),
            MIDDLE(82000, 350),
            HIGH(132000, 350);

            public final int position;
            public final double tolerance;

            private ARM_POSITIONS (int position, double tolerance) { 

                this.position = position; 
                this.tolerance = tolerance;
            }
        }

        public static final int HARD_STOP = 135000;

        public static final double ARM_KP = 0.00030;
        public static final double ARM_KI = 0.000120;
        public static final double ARM_KD = 0.000001;

        public static final int ARM_MOTOR = 12;
        public static final int ROLLER_MOTOR = 21;
        public static final int ARM_LIMIT_SWITCH = 9;

        public static final int AIR_LOCK_PISTON_FORWARD = 2; // TODO
        public static final int AIR_LOCK_PISTON_REVERSE = 3; // TODO
    }
    
    // Electronic Constants.
    public static final class ElectronicConstants {

        public static final int PNEUMATICS_HUB = 8;
        public static final int PNEUMATICS_MINIMUM_PRESSURE = 100;
        public static final int PNEUMATICS_MAXIMUM_PRESSURE = 120;

        public static final int LED_PWM = 6;
        public static final List<Integer> LED_LENGTHS = Arrays.asList(22, 64, 65, 14);
        public static final List<Integer> LED_DIRECTIONS = Arrays.asList(-1, 1, -1, 1);

        public enum LED_COLORS {
            CONE(27, 20),
            CUBE(139, 20),
            ERROR(0, 10),
            RAINBOW(90, 90);

            public final int absoluteHue;
            public final int hueDeviation;

            private LED_COLORS (int absoluteHue, int hueDeviation) {

                this.absoluteHue = absoluteHue;
                this.hueDeviation = hueDeviation;
            }
        }
    }
}
