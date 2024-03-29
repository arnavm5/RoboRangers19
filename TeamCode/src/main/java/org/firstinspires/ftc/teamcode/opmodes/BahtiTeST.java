/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.helper.AutoBot;


@Autonomous(name="Bahti Auto")

public class BahtiTeST extends OpMode{
    private AutoBot shamsBot;

    @Override
    public void init() {
        shamsBot = new AutoBot(hardwareMap, telemetry);
        shamsBot.setUpWheels();
        shamsBot.resetEncoder();
        shamsBot.setupTensorCV();
        shamsBot.setupliftmotor();
        telemetry.addData("TopLeft Encoder", shamsBot.topLeft.getCurrentPosition());
        MarkerAuto.step = 0;
    }

    /*
     * Code to run REPEATEDLY when the driver hits INIT
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        //Resetting the encoder value for the latch when starting the OpMode
        shamsBot.liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shamsBot.liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    //* Code to run REPEATEDLY when the driver hits PLAY

    @Override
    public void loop()
    {
        int step = MarkerAuto.step;
        //Displaying the En+coder Values nad Step(Debugging)
        telemetry.addData("TopLeft Encoder", shamsBot.topLeft.getCurrentPosition());
        telemetry.addData("TopRight Encoder", shamsBot.topRight.getCurrentPosition());
        telemetry.addData("BottomLeft Encoder", shamsBot.botLeft.getCurrentPosition());
        telemetry.addData("BottomRight Encoder", shamsBot.botRight.getCurrentPosition());
        telemetry.addData("liftMotor Encoder", shamsBot.liftMotor.getCurrentPosition());
        telemetry.addData("Step", step);

        //Start Detecting with Tensor
        shamsBot.detectTensor();

        //Latching Down & Aligning
        if (step == 0){
            shamsBot.latchdown();
        }
        else if(step == 1) {
            shamsBot.backward(4,0.3);
        }
        else if(step == 2){
            shamsBot.strafeleft(10, 1);
        }
        else if(step == 3) {
            shamsBot.forward(3.5,0.3);
        }
        else if(step == 4) {
            shamsBot.rotate("Right",60,0.3);
        }
        else if(step == 5) {
            shamsBot.forward(0,0);
        }
        //Middle
        else if(step >= 6  && shamsBot.pos == 0) {
            if(step == 6) {
                shamsBot.forward(39, 0.5);
            }
            else if(step == 7){
                shamsBot.forward(0,0);
            }
            else if(step == 8) {
                shamsBot.backward(8,0.5);
            }
            else if(step == 9){
                shamsBot.rotate("Right",100,0.5);
            }
            else if(step == 10)
                shamsBot.forward(85,0.5);
        }
        //Right
        else if(step >= 6 && shamsBot.pos == 1) {
            if(step == 6) {
                shamsBot.rotateRight();
            }
            else if(step == 7){
                shamsBot.forward(25,0.5);
            }
            else if(step == 8){
                shamsBot.rotate("Left",100,0.5);
            }
            else if(step == 9){
                shamsBot.forward(24,0.5);
            }
            else if(step == 10){
                shamsBot.straferight(24, 0.02);
            }
            else if(step == 11){
                shamsBot.forward(0,0);
            }
            else if(step == 12){
                shamsBot.rotate("Left",180,0.5);
            }
            else if(step == 13)
                shamsBot.forward(80,0.5);
        }
        //Left
        else if(step >= 6 && shamsBot.pos == -1) {
            if(step == 6) {
                shamsBot.rotateLeft();
            }
            else if(step == 7){
                shamsBot.forward(25,0.5);
            }
            else if(step == 8){
                shamsBot.rotate("Right",100,0.5);
            }
            else if(step == 9){
                shamsBot.forward(24,0.5);
            }
            else if(step == 10){
                shamsBot.straferight(24, 0.02);
            }
            else if(step == 11){
                shamsBot.forward(0,0);
            }
            else if(step == 12){
                shamsBot.rotate("Right",180,0.5);
            }
            else if(step == 13)
                shamsBot.forward(80,0.5);
        }
    }

    //Debugging
    /*
    @Override
    public void loop(){
        if(step == 0) {
            shamsBot.strafeleft(10, 1.0);
        }
        else if(step == 1)
        {
            shamsBot.rotate("Right",65,0.5);
            //shamsBot.topLeft.setPower(-0.5);
            //shamsBot.botRight.setPower(-0.5);
            //shamsBot.topRight.setPower(-0.5);
            //shamsBot.botLeft.setPower(-0.5);
        }
    }
    */

    @Override
    public void stop()
    {
        // Disable the detector
        shamsBot.tfod.shutdown();
    }
}