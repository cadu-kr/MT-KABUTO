package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TT-Torreta")
public class torreta extends LinearOpMode {

    DcMotor lancador;
    double pw = 0;
    boolean ad = false;
    boolean rm = false;

    DcMotor torreta;
    static final double rt = 288;
    static final int lmin = 0;
    static final int lmax = (int) rt;


    public void runOpMode(){


        lancador = hardwareMap.get(DcMotor.class, "lancador");
        lancador.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        torreta = hardwareMap.get(DcMotor.class, "torreta");
        torreta.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        torreta.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        torreta.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();

        while (opModeIsActive()) {
            double pt = gamepad2.left_stick_x;
            int ps  = torreta.getCurrentPosition();

            if (ps >= lmax && pt > 0){
                pt = 0;
            }
            else if (ps <= lmin && pt < 0) {
                pt = 0;
            }

            torreta.setPower(pt);

            if (gamepad2.dpad_up && !ad) {
                pw = pw + .1;
            } else if (gamepad2.dpad_down && !rm) {
                pw = pw - .1;
            }

            ad = gamepad2.dpad_up;
            rm = gamepad2.dpad_down;

            if (pw > 1) {
                pw = 1;
            } else if (pw < 0) {
                pw = 0;
            }

        }
    }
}
