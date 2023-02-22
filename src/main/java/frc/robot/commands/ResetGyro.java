package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ResetGyro extends InstantCommand {

    private final AHRS navX;

    public ResetGyro (AHRS navX) { this.navX = navX; }

    @Override
    public void initialize () { this.navX.zeroYaw(); }
}
