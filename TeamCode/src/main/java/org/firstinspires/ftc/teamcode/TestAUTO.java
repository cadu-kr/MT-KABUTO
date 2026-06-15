package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;


@Autonomous(name = "Test-AUTONOMO")
public class TestAUTO extends LinearOpMode {

    private Follower follower;
    private PathChain andar, andar2, intake, intakeb;
    private DcMotor lancador, intake1, intake2;
    private Servo alavanca;


    private ElapsedTime temp = new ElapsedTime();

    private enum estado{
        anda, volta, pegar,
        lanca1, rservo1,
        lanca2, rservo2,
        lanca3, rservo3,
        lanca4, rservo4,
        lanca5, rservo5,
        lanca6, rservo6, fim;
    }
    private estado etat = estado.anda;

    private final Pose startp = new Pose(56, 8., Math.toRadians(90));
    private final Pose lancar = new Pose(56, 36, Math.toRadians(-90));
    private final Pose pegar = new Pose(56, 56, Math.toRadians(-90));

    public void buildPaths() {

        andar = follower.pathBuilder()
                .addPath(new BezierLine(startp, lancar))
                .setLinearHeadingInterpolation(startp.getHeading(), lancar.getHeading())
                .build();

        andar2 = follower.pathBuilder()
                .addPath(new BezierLine(lancar, startp))
                .setLinearHeadingInterpolation(lancar.getHeading(), startp.getHeading())
                .build();

        intake = follower.pathBuilder()
                .addPath(new BezierLine(lancar, pegar))
                .setLinearHeadingInterpolation(lancar.getHeading(), pegar.getHeading())
                .build();

        intakeb = follower.pathBuilder()
                .addPath(new BezierLine(pegar, lancar))
                .setLinearHeadingInterpolation(pegar.getHeading(), lancar.getHeading())
                .build();


    }
    @Override
    public void runOpMode() {


        lancador = hardwareMap.get(DcMotor.class, "lancador");
        alavanca = hardwareMap.get(Servo.class, "alavanca");
        intake1 = hardwareMap.get(DcMotor.class, "intakeD");
        intake2 = hardwareMap.get(DcMotor.class, "intakeE");

        intake2.setDirection(DcMotor.Direction.REVERSE);

        follower = Constants.createFollower(hardwareMap);

        alavanca.scaleRange(0, 270);
        alavanca.setPosition(0.025);

        follower.setStartingPose(startp);
        buildPaths();
        waitForStart();

        if (opModeIsActive()) {
            follower.followPath(andar, true);
        }


        while (opModeIsActive()) {
            follower.update();

            switch (etat){
                case anda:
                    if (!follower.isBusy()){
                        lancador.setPower(0.6);
                        temp.reset();
                        etat = estado.lanca1;
                    }
                    break;
                case lanca1:
                    if (temp.milliseconds() >= 2000){
                        alavanca.setPosition(0.3);
                        temp.reset();
                        etat = estado.rservo1;
                    }
                    break;
                case  rservo1:
                    if (temp.milliseconds() >= 1500){
                        alavanca.setPosition(0.025);
                        temp.reset();
                        etat = estado.lanca2;
                    }
                    break;
                case lanca2:
                    if (temp.milliseconds() >= 1500){
                        alavanca.setPosition(0.3);
                        temp.reset();
                        etat = estado.rservo2;
                    }
                    break;
                case rservo2:
                    if (temp.milliseconds() >= 1500){
                        alavanca.setPosition(0.025);
                        temp.reset();
                        etat = estado.lanca3;
                    }
                    break;
                case lanca3:
                    if (temp.milliseconds() >= 1500){
                        alavanca.setPosition(0.3);
                        temp.reset();
                        etat = estado.rservo3;
                    }
                    break;
                case rservo3:
                    if (temp.milliseconds() >= 1500){
                        alavanca.setPosition(0.025);
                        lancador.setPower(0);
                        intake1.setPower(1);
                        intake2.setPower(1);
                        follower.followPath(intake, true);
                        etat = estado.pegar;
                    }
                    break;
                case pegar:
                    if (!follower.isBusy()){
                        intake1.setPower(0);
                        intake2.setPower(0);
                        follower.followPath(intakeb, true);
                        etat = estado.volta;
                    }
                    break;
                case volta:
                    if (!follower.isBusy()){
                        lancador.setPower(0.6);
                        temp.reset();
                        etat = estado.lanca4;
                    }
                    break;
                case lanca4:
                    if (temp.milliseconds() >= 1500){
                        alavanca.setPosition(0.3);
                        temp.reset();
                        etat = estado.rservo4;
                    }
                    break;
                case rservo4:
                    if (temp.milliseconds() >= 1500){
                        alavanca.setPosition(0.025);
                        temp.reset();
                        etat = estado.lanca5;
                    }
                    break;
                case lanca5:
                    if (temp.milliseconds() >=1500){
                        alavanca.setPosition(0.3);
                        temp.reset();
                        etat = estado.rservo5;
                    }
                    break;
                case rservo5:
                    if (temp.milliseconds() >= 1500){
                        alavanca.setPosition(0.025);
                        temp.reset();
                        etat = estado.lanca6;
                    }
                    break;
                case lanca6:
                    if (temp.milliseconds() >= 1500){
                        alavanca.setPosition(0.3);
                        temp.reset();
                        etat = estado.rservo6;
                    }
                    break;
                case rservo6:
                    if (temp.milliseconds() >= 1500){
                        alavanca.setPosition(0.025);
                        lancador.setPower(0);
                        follower.followPath(andar2, true);
                        temp.reset();
                        etat = estado.fim;
                    }
                    break;
                case fim:
                    break;
            }
        }
    }
}