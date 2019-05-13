# Hardware

The meta-rpi-home layer works on top of below devices:
1. [Raspberry Pi 3 Model B+][1]
2. [Raspberry Pi Touch Display][2]

Device assembly instructions can be found at:
https://www.element14.com/community/docs/DOC-78156/l/raspberry-pi-7-touchscreen-display

Due to lack of USB keyboard, use below cables for interacting with the device:
1. TTL-to-USB (3.3v) cable
2. Ethernet cable (no need of crossover cable with auto MDI-X capable port)

Below diagram illustrates the connection of above devices:

                                             +---------------+
                                             | Touch Display |
       +---------------------+               +---------------+
       | (Raspberry Pi 3 B+) |    +----------| 5V  (red)     |
       +---------------------+    | +--------| GND (black)   |
       |        5v Power:  2 |----+ |        +---------------+
       |        5v Power:  4 |-x    |
       |          Ground:  6 |------+        +---------------+
       |             TXD:  8 |----+          |  TTL-to-USB   |
       |             RXD: 10 |----|-+        +---------------+
       |            PWM0: 12 |-x  | |        | PWR           |
     +-| ETH      Ground: 14 |--+ | +--------| TXD           |
     | +---------------------+  | +----------| RXD           |
     |                          +------------| GND       USB |-+
     |                                       +---------------+ |
     |                                                         |
     |               +---------------------+                   |
     |               | Linux Build Host PC |                   |
     |               +---------------------+                   |
     +---------------| ETH             USB |-------------------+
                     +---------------------+

**NOTE**: Depending on the USB hub the TTL-to-USB connected to, it may provide
just 500mA according to the USB standard, which is not sufficient to drive both
Raspberry Pi and the Touch Display, thus leaves the TTL-to-USB PWR unconnected
and always use the official 2.5A power supply.

# Software

Instructions to setup the proper Yocto build host can be found at:
https://www.yoctoproject.org/docs/current/brief-yoctoprojectqs/brief-yoctoprojectqs.html

CONVENTION for commands listed below:
- '>' denotes commands typed in PC terminal
- '$' denotes commands typed in Pi terminal

The [`repo`][3] is used to put all dependencies of rpi-home together, make sure
it installed as well, then follow below steps:
```sh
> cd <project directory>
> repo init -u git@github.com:zhu-yi/repo-manifest.git
> repo sync
> cd poky
> . oe-init-build-env
> bitbake rpi-home-base

# insert sd card to build host, make sure use 'lsblk' to find the correct '/dev/sd?'
> umount /dev/sdc*
> sudo dd if=tmp/deploy/images/raspberrypi3/rpi-home-base-raspberrypi3.rpi-sdimg of=/dev/sdc bs=4M status=progress && sync
# insert sd card to raspberry pi, and then power on
```

# Smoke Test

## Boot Sequence
1. To be added (power on -> bootcode.bin -> start.elf -> ...)

## Serial
```sh
# console over serial ("ctrl + a, d" for quit)
> screen /dev/ttyUSB0 115200
```

## Network (Ethernet, WIFI, mDNS)
```sh
# test mDNS is working with direct Ethernet connection
> ping rpi3.local

# console over ssh
> ssh root@rpi3.local

# Wifi connection settings (once for each ssid)
$ wpa_passphrase <ssid> <pwd> >> /etc/wpa_supplicant/wpa_supplicant-wlan0.conf

# or reboot
$ systemctl restart wpa_supplicant@wlan0.service
$ ifconfig
$ curl wttr.in
$ wget <url>
```

## I2C

## SPI

## CAN

## USB

## Bluetooth
```sh
$ bluetoothctl
[bluetooth]# power on
[bluetooth]# scan on
[bluetooth]# pair <PC bluetooth MAC address>
[agent] Enter Pin code: <pin code of PC>
[bluetooth]# discoverable on
[bluetooth]# quit

# hacky and insecure
# not figure out how to make "getty rfcomm0 115200 -a root" work...
$ rfcomm watch hci0 1 getty -n -l /bin/bash rfcomm0 115200

# console over bluetooth (cpu load up to 35.3%)
> sudo rfcomm bind rfcomm0 <rpi3 bluetooth MAC address>
> screen /dev/rfcomm0 115200
```

## Touch Screen
```sh
# use utilities from tslib (capacitive touchscreen don't need calibration)
$ ts_print
$ ts_print_mt
$ ...
```
## Graphic
```
# expected to see "No space left on device" error
#!/bin/sh

while : ; do
	cp /dev/urandom /dev/fb0
done
```

```sh
$ wget https://www.kernel.org/theme/images/logos/tux.png
$ wget https://www.gnu.org/graphics/heckert_gnu.transp.small.png
$ fbi -T 1 tux.png heckert_gnu.transp.small.png -t 1
```

## Audio

## Video
```sh
# if rpi-home-multimedia (-/+, </>, 1/2, see https://elinux.org/omxplayer)
$ omxplayer -s /usr/share/movies/big_buck_bunny_1080p_surround.avi
```

## MMC
```sh
$ mount /dev/mmcblk0p1 /boot
```

# Yocto Stuff

```sh
# grep for (S, B, FILE, PF, PACKAGES, T...)
> bitbake -e <recipe> | grep "^S="
> bitbake -c listtasks <recipe>
> bitbake -c devshell <recipe>
> bitbake -g -I virtual/kernel -I eglibc <recipe>

# verbose log of bitbake: .../tmp/log/cooker/<machine>

```

[1]: https://www.raspberrypi.org/products/raspberry-pi-3-model-b-plus/
[2]: https://www.raspberrypi.org/products/raspberry-pi-touch-display/
[3]: https://source.android.com/setup/build/downloading
