// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 5;
  }

  public static class OuttakeConstants {
    public static final int outtakeMotorPort = 1;
  }

  public static class IntakeConstants {
    public static final int intakeMotorPort = 6;
  }

  public static class DriveConstants {
    // Constants for motor ports to clarify which one the RoboRio should send electricity too.
    public static final int leftfrontWheel = 7;
    public static final int rightfrontWheel = 2;

    // Measurements of the real robot, used by the drivetrain simulation in DrivebaseSubsystem.
    // TODO (students): Measure or look up each value and replace the 0 placeholders.
    //   Use meters and kilograms. The simulation is only as accurate as these numbers.

    // How many motors drive each side of the robot (including any follower motors).
    public static final int kMotorsPerSide = 0;

    // Gear ratio: how many times the motor spins for one spin of the wheel. For example, a
    // 10.71:1 gearbox is 10.71.
    public static final double kGearRatio = 0.0;

    // Diameter of one drive wheel, in meters. (1 inch = 0.0254 meters.)
    public static final double kWheelDiameterMeters = 0.0;

    // Distance between the middle of the left wheels and the middle of the right wheels, in
    // meters.
    public static final double kTrackWidthMeters = 0.0;

    // Mass of the robot with battery and bumpers, in kilograms. (1 pound = 0.4536 kg.)
    public static final double kRobotMassKg = 0.0;

    // How hard the robot is to spin, in kg*m^2 (its "moment of inertia"). This is hard to
    // measure, so a rough estimate is fine. A typical FRC robot is somewhere around 3 to 8.
    public static final double kMomentOfInertia = 0.0;
  }
}
