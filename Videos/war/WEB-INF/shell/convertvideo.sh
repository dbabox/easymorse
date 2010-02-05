#!/bin/bash

infile="$1"
tmpfile="video_tmp.mp4"
outfile="$2"
options="-vcodec libx264 -b 512k -flags +loop+mv4 -cmp 256 \
-partitions +parti4x4+parti8x8+partp4x4+partp8x8+partb8x8 \
-me_method hex -subq 7 -trellis 1 -refs 5 -bf 0 \
-flags2 +mixed_refs -coder 0 -me_range 16 \
-g 250 -keyint_min 25 -sc_threshold 40 -i_qfactor 0.71 -qmin 10 \
-qmax 51 -qdiff 4"
ffmpeg -y -i "$infile" -an -pass 1 -threads 2 $options "$tmpfile"
ffmpeg -y -i "$infile" -acodec libfaac -ar 44100 -ab 96k -pass 2 -threads 2 $options "$outfile"
