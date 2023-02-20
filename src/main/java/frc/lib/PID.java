package frc.lib;

import edu.wpi.first.wpilibj.Timer;

public class PID {
    
    private final Timer timer;
    private boolean hasRun = false;

    private double lastError = 0;
    private double cumulativeError = 0;

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
        this.cumulativeError += (error * dt);

        double p = this.kP * error;
        double i = this.kI * this.cumulativeError;
        double d = this.kD * (error - this.lastError) / dt;

        if (error > 30000 || p < 0) { i = 0.0; }

        if (!this.hasRun) { 
            
            this.hasRun = true;
            return 0.0;
        }

        System.out.println("p: " + p + ", i: " + i);

        this.timer.reset();
        this.lastError = error;
        return p + i + d;
    }
}
