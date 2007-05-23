/**
 *
 */
package ucl.cs.testingEmulator.bluetooth;

import java.util.Hashtable;

import javax.bluetooth.DeviceClass;

/**
 * @author -Michele Sama- aka -RAX-
 * 
 * University College London
 * Dept. of Computer Science
 * Gower Street
 * London WC1E 6BT
 * United Kingdom
 *
 * Email: M.Sama (at) cs.ucl.ac.uk
 *
 * Group:
 * Software Systems Engineering
 *
 */
public class DeviceClassWrapper {

	public static Hashtable<String, Hashtable<String, Integer>> classes=new Hashtable<String, Hashtable<String, Integer>>();
	public static Hashtable<String, Integer> majorClass=new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> minor_miscellaneous=new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> minor_computer=new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> minor_phone=new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> minor_lan=new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> minor_audio_video=new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> minor_peripheral=new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> minor_imaging=new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> minor_wearable=new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> minor_toy=new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> minor_uncategorized=new Hashtable<String, Integer>();
	
	static
	{
		majorClass.put("Miscellaneous", 0*256);
		majorClass.put("Computer", 1*256);
		majorClass.put("Phone", 2*256);
		majorClass.put("LAN/Network Access Point", 3*256);
		majorClass.put("Audio/Video", 4*256);
		majorClass.put("Peripheral", 5*256);
		majorClass.put("Imaging", 6*256);
		majorClass.put("Wearable", 7*256);
		majorClass.put("Toy", 8*256);
		majorClass.put("Uncategorized", 31*256);
		
		//Minor class Miscellaneous
		classes.put("Miscellaneous", minor_miscellaneous);
		minor_miscellaneous.put("Default", 0);
		
		//Minor class Computer
		classes.put("Computer", minor_computer);
		minor_computer.put("Uncategorized", 0*4);
		minor_computer.put("Desktop/Workstation", 1*4);
		minor_computer.put("Server-class", 2*4);
		minor_computer.put("Laptop", 3*4);
		minor_computer.put("Handheld PC/PDA  (clam shell)", 4*4);
		minor_computer.put("Palm sized PC/PDA", 5*4);
		minor_computer.put("Wearable computer", 6*4);
		
		//Minor class Phone
		classes.put("Phone", minor_phone);
		minor_phone.put("Uncategorized", 0*4);
		minor_phone.put("Cellular", 1*4);
		minor_phone.put("Cordless", 2*4);
		minor_phone.put("Smart phone", 3*4);
		minor_phone.put("Wired modem or voice gateway", 4*4);
		minor_phone.put("Common ISDN access", 5*4);
		
		//Minor class LAN/Network Access Point
		classes.put("LAN/Network Access Point", minor_lan);
		minor_lan.put("Fully available", 0*32);
		minor_lan.put("1-17%", 1*32);
		minor_lan.put("17-33%", 2*32);
		minor_lan.put("33-50%", 3*32);
		minor_lan.put("50-67%", 4*32);
		minor_lan.put("67-83%", 5*32);
		minor_lan.put("83-99%", 6*32);
		minor_lan.put("No service available", 7*32);
		
		//Minor class Audio/Video
		classes.put("Audio/Video", minor_audio_video);
		minor_audio_video.put("Uncategorized", 0*4);
		minor_audio_video.put("Wearable headset device", 1*4);
		minor_audio_video.put("Hands-free device", 2*4);
		//3 is reserved
		minor_audio_video.put("Microphone", 4*4);
		minor_audio_video.put("Loudspeaker", 5*4);
		minor_audio_video.put("Headphone", 6*4);
		minor_audio_video.put("Portable audio", 7*4);
		minor_audio_video.put("Car audio", 8*4);
		minor_audio_video.put("Set-top box", 9*4);
		minor_audio_video.put("HiFi audio device", 10*4);
		minor_audio_video.put("VCR", 11*4);
		minor_audio_video.put("Video Camera", 12*4);
		minor_audio_video.put("Camcorder", 13*4);
		minor_audio_video.put("Video monitor", 14*4);
		minor_audio_video.put("Video display and loudspeaker", 15*4);
		minor_audio_video.put("Video conferencing", 16*4);
		//17 is reserved
		minor_audio_video.put("Gaming/Toy", 18*4);
		
		//Minor class Peripheral
		classes.put("Peripheral", minor_peripheral);
		minor_peripheral.put("Not keyboard/Not pointing device - Uncategorized", (0)*4);
		minor_peripheral.put("Not keyboard/Not pointing device - Joystick", (1)*4);
		minor_peripheral.put("Not keyboard/Not pointing device - Gamepad", (2)*4);
		minor_peripheral.put("Not keyboard/Not pointing device - Remote control", (3)*4);
		minor_peripheral.put("Not keyboard/Not pointing device - Sensing device", (4)*4);
		minor_peripheral.put("Not keyboard/Not pointing device - Digitizer tablet", (5)*4);
		minor_peripheral.put("Not keyboard/Not pointing device - Card Reader", (6)*4);
		//Keyboard
		minor_peripheral.put("Keyboard - Uncategorized", 1*64+0*4);
		minor_peripheral.put("Keyboard - Joystick", 1*64+1*4);
		minor_peripheral.put("Keyboard - Gamepad", 1*64+2*4);
		minor_peripheral.put("Keyboard - Remote control", 1*64+3*4);
		minor_peripheral.put("Keyboard - Sensing device", 1*64+4*4);
		minor_peripheral.put("Keyboard - Digitizer tablet", 1*64+5*4);
		minor_peripheral.put("Keyboard - Card Reader", 1*64+6*4);
		//Pointing device
		minor_peripheral.put("Pointing device - Uncategorized", 2*64+0*4);
		minor_peripheral.put("Pointing device - Joystick", 2*64+1*4);
		minor_peripheral.put("Pointing device - Gamepad", 2*64+2*4);
		minor_peripheral.put("Pointing device - Remote control", 2*64+3*4);
		minor_peripheral.put("Pointing device - Sensing device", 2*64+4*4);
		minor_peripheral.put("Pointing device - Digitizer tablet", 2*64+5*4);
		minor_peripheral.put("Pointing device - Card Reader", 2*64+6*4);
		//Combo keyboard/pointing device
		minor_peripheral.put("Combo keyboard/pointing device - Uncategorized", 3*64+0*4);
		minor_peripheral.put("Combo keyboard/pointing device - Joystick", 3*64+1*4);
		minor_peripheral.put("Combo keyboard/pointing device - Gamepad", 3*64+2*4);
		minor_peripheral.put("Combo keyboard/pointing device - Remote control", 3*64+3*4);
		minor_peripheral.put("Combo keyboard/pointing device - Sensing device", 3*64+4*4);
		minor_peripheral.put("Combo keyboard/pointing device - Digitizer tablet", 3*64+5*4);
		minor_peripheral.put("Combo keyboard/pointing device - Card Reader", 3*64+6*4);

		//Minor class Imaging
		classes.put("Imaging", minor_imaging);
		minor_imaging.put("Uncategorized", (0)*16);
		minor_imaging.put("Display", (1)*16);
		minor_imaging.put("Camera", (2)*16);
		minor_imaging.put("Camera - Display", (2+1)*16);
		minor_imaging.put("Scanner", (4)*16);
		minor_imaging.put("Scanner - Display", (4+1)*16);
		minor_imaging.put("Scanner - Camera", (4+2)*16);
		minor_imaging.put("Scanner - Camera - Display", (4+2+1)*16);
		minor_imaging.put("Printer", (8)*16);
		minor_imaging.put("Printer - Display", (8+1)*16);
		minor_imaging.put("Printer - Camera", (8+2)*16);
		minor_imaging.put("Printer - Camera - Display", (8+2+1)*16);
		minor_imaging.put("Printer - Scanner", (8+4)*16);
		minor_imaging.put("Printer - Scanner -Display", (8+4+1)*16);
		minor_imaging.put("Printer - Scanner - Camera", (8+4+2)*16);
		minor_imaging.put("Printer - Scanner - Camera - Display", (8+4+2+1)*16);
		
		//Minor class Wearable
		classes.put("Wearable", minor_wearable);
		minor_wearable.put("Wrist watch", 1*4);
		minor_wearable.put("Pager", 2*4);
		minor_wearable.put("Jacket", 3*4);
		minor_wearable.put("Helmet", 4*4);
		minor_wearable.put("Glasses", 5*4);
		
		//Minor class Toy
		classes.put("Toy", minor_toy);
		minor_toy.put("Robot", 1*4);
		minor_toy.put("Vehicle", 2*4);
		minor_toy.put("Doll/Action figure", 3*4);
		minor_toy.put("Controller", 4*4);
		minor_toy.put("Game", 5*4);
		
		//Minor class Uncategorized
		classes.put("Uncategorized", minor_uncategorized);
		minor_uncategorized.put("Default", 0);
	}
	
	
	public DeviceClass createDeviceClass(int service, int major, int minor, int format)
	{
		if(format>=4&&format<0)
		{
			throw new java.lang.IllegalArgumentException("Format must be 0-"+(4));
		}
		if(minor>=Math.pow(2, 8)&&minor%2!=0)
		{
			throw new java.lang.IllegalArgumentException("Format must be 0-"+(256));
		}
		return new DeviceClass(service+major+minor+format);
	}
	
	public DeviceClass createDeviceClass(String majorS,String minorS)
	{
		int service=0;
		int major=majorClass.get(majorS).intValue();
		int minor=classes.get(majorS).get(minorS).intValue();
		int format=0;
		return createDeviceClass( service, major, minor, format);
	}
	
	
	
}
