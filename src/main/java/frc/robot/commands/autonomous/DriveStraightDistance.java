package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;


import frc.robot.Constants;
import frc.robot.subsystems.WestCoastDrive;

public class DriveStraightDistance extends CommandBase {
    
    final private WestCoastDrive westCoastDrive;
    final private double meters;
    final private double power;
    public long startTime;

    public DriveStraightDistance (WestCoastDrive westCoastDrive, double meters, double power) {

        this.westCoastDrive = westCoastDrive;
        this.meters = meters;
        this.power = power;
         //new edu.wpi.first.wpilibj.Timer();
         //startTime =Java.util.Timer;
         System.out.println("M: " + meters + "p:" + power);
        this.addRequirements(this.westCoastDrive);
    }

    @Override
    public void initialize () {
        //this.getController().disableContinuousInput();
        //this.westCoastDrive.resetDistance();
        startTime = System.currentTimeMillis();
        System.out.println("***Reset startTime to: " + startTime);

        this.westCoastDrive.resetHeading();
    }

    @Override
    public void execute () {

        double powerValue = Math.signum(this.meters) * this.power;
       // System.out.println("Power:" + this.power);
        double turningValue = this.westCoastDrive.getHeading() * Constants.AutonomousConstants.GYRO_STRAIGHT_KP;
        this.westCoastDrive.drive(powerValue, turningValue);
    }

    @Override
    public boolean isFinished () { 
        long delta = (System.currentTimeMillis() - startTime);
        boolean done = ((delta/2500.0) >= Math.abs(this.meters)); 
        if (done){
        System.out.println("E time:" + delta/2500.0 + "Target:" + Math.abs(this.meters) + "Done:" + done);
        }        
    
        return done; }

    @Override
    public void end (boolean interrupted) { this.westCoastDrive.stop(); }
}
