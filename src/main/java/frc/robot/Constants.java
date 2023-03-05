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

    // Intaker Constants.
    public static final class IntakerConstants {

        public static final int SLIDER_MOTOR = 10;
        public static final int ROLLER_MOTOR = 20;

        public enum SLIDER_POSITIONS {
            TEST(30000);

            public final int position;
            private SLIDER_POSITIONS (int position) { this.position = position; }
        }

        public static final double SLIDER_KP = 0.00001;
        public static final double SLIDER_KI = 0.0;
        public static final double SLIDER_KD = 0.0;

        public static final int SLIDER_LIMIT_SWITCH = 7;
    }
    
    // Indexer Constants.
    public static final class IndexerConstants {

        public static final int INDEXER_MOTOR = 11;
    }
    
    // Manipulator Constants.
    public static final class ManipulatorConstants {

        public enum ARM_POSITIONS {
            MIDDLE(39000, false),
            STATION(52000, true),
            TOP(56000, true);

            public final int position;
            public final boolean useIntegral;

            private ARM_POSITIONS (int position, boolean useIntegral) { 
                
                this.position = position; 
                this.useIntegral = useIntegral;
            }
        }

        public static final double ARM_KP = 0.00006;
        public static final double ARM_KI = 0.00008;
        public static final double ARM_KD = 0.0;

        public static final int ARM_MOTOR = 12;
        public static final int ROLLER_MOTOR = 21;

        public static final int ARM_LIMIT_SWITCH = 9;
        public static final int CONE_BEAM_BREAK = 0; // TODO
        public static final int CUBE_BEAM_BREAK = 0; // TODO
    }
    
    // Electronic Constants.
    public static final class ElectronicConstants {

        public static final int LED_PWM = 0;
        public static final List<Integer> LED_LENGTHS = Arrays.asList(22, 64, 65, 14);
        public static final List<Integer> LED_DIRECTIONS = Arrays.asList(-1, 1, -1, 1);

        public enum LED_COLORS {
            CONE(27, 20),
            CUBE(154, 20),
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
