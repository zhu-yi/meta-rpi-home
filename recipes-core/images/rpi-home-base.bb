# Copyright (C) 2019 Zhu Yi <dev.yi.zhu@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "A base image for rpi-home device."
LICENSE = "MIT"

inherit core-image

IMAGE_FEATURES += " \
    ssh-server-openssh \
    ${@bb.utils.contains("DISTRO_FEATURES", "x11", "x11-base","", d)} \
"

IMAGE_INSTALL += " \
    avahi-daemon \
    iw \
    wpa-supplicant \
"

IMAGE_INSTALL += " \
    can-utils \
    coreutils \
    curl \
    ethtool \
    evtest \
    fb-test \
    fbida \
    gdbserver \
    i2c-tools \
    iputils \
    iproute2 \
    memtool \
    strace \
    spitools \
    systemd-analyze \
    tslib-tests \
    usbutils \
    logrotate \
"

