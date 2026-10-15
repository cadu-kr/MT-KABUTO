package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter{

    DcMotor shooter;
    double pw = .6;
    boolean ad = false;
    boolean rm = false;

    public void inicio(HardwareMap hardwareMap) {
        shooter = hardwareMap.get(DcMotor.class, "shooter");
    }
    public void rodar(Gamepad gamepad1){
        if (gamepad1.dpad_up && !ad) {
            pw+=.1;
        }
        else if (gamepad1.dpad_down && !rm) {
            pw-=.1;
        }
        ad = gamepad1.dpad_up;
        rm = gamepad1.dpad_down;

        if (pw > 1) {
            pw = 1;
        }
        else if (pw < -1) {
            pw = -1;
        }

        if (gamepad1.right_trigger >= .2) {
            shooter.setPower(pw);
        }
        else {
            shooter.setPower(0);
        }
    }
    public void telemetry(Telemetry telemetry){
        telemetry.addData("Potência: ", pw);
    }
}
