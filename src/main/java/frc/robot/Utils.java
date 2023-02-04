package frc.robot;

public class Utils {
    
    public static double motorRotationsToMeters (double RPM) {

        double wheelRotations = RPM / Constants.WestCoastConstants.MOTOR_GEAR_RATIO;
        double wheelMeters = wheelRotations * (Constants.WestCoastConstants.WHEEL_DIAMETER * Math.PI);
        return wheelMeters;
    }

    public static double metersToMotorRotations (double meters) {

        double wheelRotations = meters / (Constants.WestCoastConstants.WHEEL_DIAMETER * Math.PI);
        double motorRotations = wheelRotations * Constants.WestCoastConstants.MOTOR_GEAR_RATIO;
        return motorRotations;
    }
}