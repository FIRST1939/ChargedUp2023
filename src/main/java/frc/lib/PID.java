package frc.lib;

import edu.wpi.first.wpilibj.Timer;

public class PID {
    
    private final Timer timer;
    private double lastError = 0;

    public final double kP;
    public final double kI;
    public final double kD;

    public PID (double kP, double kI, double kD) {

        this.timer = new Timer();
        this.timer.start();

        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double calculate(double error) {

        double dt = this.timer.get();
        this.timer.reset();

        double p = this.kP * error;
        double i = this.kI * error * dt;
        double d = this.kD * (error - this.lastError) / dt;

        this.lastError = error;
        return p + i + d;
    }
}
