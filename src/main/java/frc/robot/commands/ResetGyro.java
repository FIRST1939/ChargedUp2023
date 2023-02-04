package frc.robot.commands;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ResetGyro extends CommandBase {

    private final AHRS navX;

    public ResetGyro (AHRS navX) {

        this.navX = navX;
    }

    @Override
    public void initialize () {

        this.navX.zeroYaw();
    }

    @Override
    public boolean isFinished () {

        return true;
    }
}
