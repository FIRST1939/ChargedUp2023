package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Photonvision extends SubsystemBase {

    private final PhotonCamera photonCamera;
    private List<PhotonTrackedTarget> photonTrackedTargets = new ArrayList<PhotonTrackedTarget>();
    private int currentTargets = 0;

    public Photonvision (PhotonCamera photonCamera) { this.photonCamera = photonCamera; }

    public void periodic () {

        PhotonPipelineResult photonPipelineResult = this.photonCamera.getLatestResult();
        if (!photonPipelineResult.hasTargets()) {
            
            this.photonTrackedTargets.clear();
            this.currentTargets = 0;
        } else {

            this.photonTrackedTargets = photonPipelineResult.getTargets();
            this.currentTargets = this.photonTrackedTargets.size();
        }
    }

    public double getXDistance () {

        PhotonPipelineResult photonPipelineResult = this.photonCamera.getLatestResult();
        if (!photonPipelineResult.hasTargets()) { return 0.0; }

        PhotonTrackedTarget photonTrackedTarget = photonPipelineResult.getBestTarget();
        return photonTrackedTarget.getBestCameraToTarget().getX();
    }

    public double getBestRelativeX () {

        PhotonPipelineResult photonPipelineResult = this.photonCamera.getLatestResult();
        if (!photonPipelineResult.hasTargets()) { return 0.0; }

        return this.getRelativeX(photonPipelineResult.getBestTarget());
    }

    public double getRelativeX (PhotonTrackedTarget photonTrackedTarget) {

        double positionX = photonTrackedTarget.getBestCameraToTarget().getX();
        int tagID = photonTrackedTarget.getFiducialId();
        double transformX = 0.0;

        if (tagID == 1) { transformX = Constants.AutonomousConstants.APRILTAG_POSITIONS.one.x; } 
        else if (tagID == 2) { transformX = Constants.AutonomousConstants.APRILTAG_POSITIONS.two.x; } 
        else if (tagID == 3) { transformX = Constants.AutonomousConstants.APRILTAG_POSITIONS.three.x; }

        transformX *= -1;
        positionX += transformX;
        return positionX;
    }
}
