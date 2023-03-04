package frc.lib;

import edu.wpi.first.wpilibj.Timer;

public class PID {
    
    private final Timer timer;
    private boolean hasRun = false;

    private double lastError = 0;
    private double cumulativeError = 0;
    private final int integralActivation;
    private final boolean useIntegral;

    public final double kP;
    public final double kI;
    public final double kD;

    public PID (double kP, double kI, double kD, int integralActivation, boolean useIntegral) {

        this.timer = new Timer();
        this.timer.start();
        this.integralActivation = integralActivation;
        this.useIntegral = useIntegral;

        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double calculate(double error) {

        double dt = this.timer.get();
        if (error <= this.integralActivation) { this.cumulativeError += (error * dt); }

        double p = this.kP * error;
        double i = this.kI * this.cumulativeError;
        double d = this.kD * (error - this.lastError) / dt;

        if (error > this.integralActivation || p < 0) { i = 0.0; }

        if (!this.hasRun) { 
            
            this.hasRun = true;
            return 0.0;
        }

        this.timer.reset();
        this.lastError = error;

        if (this.useIntegral) { return p + i + d; }
        return p + d;
    }
}
