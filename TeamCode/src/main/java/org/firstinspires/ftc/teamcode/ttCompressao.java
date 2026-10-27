package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TT-Compressao", group = "TESTE")
public class ttCompressao extends OpMode {
    Servo servo;
    boolean addCompressao = false;
    boolean removeCompressao = false;
    double compressao = 0;

    public void compressaoServo() {
        if (gamepad1.dpad_up && !addCompressao) {
            compressao+= .01;
        }
        else if (gamepad1.dpad_down && !removeCompressao) {
            compressao-= .01;
        }
        addCompressao = gamepad1.dpad_up;
        removeCompressao = gamepad1.dpad_down;

        if (gamepad1.right_bumper) {
            servo.setPosition(compressao);
        }
        else {
            servo.setPosition(0);
        }
    }

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, "compressao");
        servo.scaleRange(0, 270);
    }

    @Override
    public void loop() {
        compressaoServo();
    }
}
