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

        public static final double GYRO_STRAIGHT_KP = 0.005;
        public static final double GYRO_TURNING_KP = 0.02;

        public static final double TURNING_ANGLE_TOLERANCE = 5.0;
        public static final double TURNUNG_ANGLE_TURN_RATE_TOLERANCE = 10.0;
        public static final double TURNING_ANGLE_KF = 0.2;

        public enum APRILTAG_POSITIONS {
            one(2.76, -1.79),
            two(2.76, 0.0),
            three(2.76, 1.79);

            public final double x;
            public final double y;

            private APRILTAG_POSITIONS (double x, double y) {

                this.x = x;
                this.y = y;
            }
        }
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
    
    // Manipulator Constants.
    public static final class ManipulatorConstants {

        public static final int ARM_MINIMUM_EXTENSION = 0; // TODO
        public static final int ARM_MAXIMUM_EXTENSION = 78000; // TODO

        public enum ARM_POSITIONS {
            MIDDLE(38000),
            STATION(58000), // TODO
            TOP(62000);

            public final int position;
            private ARM_POSITIONS (int position) { this.position = position; }
        }

        public static final double ARM_KP = 0.00006;
        public static final double ARM_KI = 0.0000000444;
        public static final double ARM_KD = 0.0;

        public static final int ARM_MOTOR = 12;
        public static final int ROLLER_MOTOR = 21;
    }
    
    // Electronic Constants.
    public static final class ElectronicConstants {

        public static final List<Integer> LED_PWMS = Arrays.asList(9); // TODO
        public static final List<Integer> LED_LENGTHS = Arrays.asList(37); // TODO

        public enum LED_COLORS {
            CONE(27, 14),
            CUBE(128, 2),
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
