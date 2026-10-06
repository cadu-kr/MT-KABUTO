package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "TT-Torreta")
public class torreta extends LinearOpMode {

    DcMotor lancador;
    double pw = 0;
    boolean ad = false;
    boolean rm = false;

    DcMotor torreta;
    static final double rt = 288;
    static final int lmin = (int) -rt;
    static final int lmax = (int) rt;
    static final double vslime = 0.025;

    Limelight3A lime;


    public void runOpMode(){


        lancador = hardwareMap.get(DcMotor.class, "lancador");
        lancador.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        torreta = hardwareMap.get(DcMotor.class, "torreta");
        torreta.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        torreta.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        torreta.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lime = hardwareMap.get(Limelight3A.class, "lime");
        lime.pipelineSwitch(0);
        lime.start();


        waitForStart();

        while (opModeIsActive()) {
            int ps = torreta.getCurrentPosition();
            double pt = 0;

            LLResult result = lime.getLatestResult();

            if (result != null && result.isValid()) {
                double tx = result.getTx();
                pt = tx * vslime;
            }
            else {
                pt = gamepad2.left_stick_x;
            }


            if (ps >= lmax && pt > 0) {
                pt = 0;
            } else if (ps <= lmin && pt < 0) {
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

            lancador.setPower(pw);

            telemetry.addData("Posição Torreta (ticks)", ps);
            telemetry.addData("Potência Lançador", pw);
            telemetry.addData("Tag Visível", (result != null && result.isValid()));
            telemetry.update();
        }

        lime.stop();
    }
}