use warnings;
use strict;

my $permLen = $ARGV[0];
my $numPerms = $ARGV[1];
my $goodBad = $ARGV[2];

open(my $permsFile, '>', "permutations.in") or die "Couldn't open output file!\n";
print $permsFile $numPerms . "\n";

if($goodBad eq "good") {
	print "Generating $numPerms good permutations of $permLen numbers\n";
} else {
	print "Generating $numPerms bad permutations of $permLen numbers\n";
}

my @averages;
for(my $i = 0; $i < $permLen; $i++) {
	$averages[$i] = 0;
}

for(my $i = 0; $i < $numPerms; $i++) {
	my @permutation;

	for(my $j = 0; $j < $permLen; $j++) {
		$permutation[$j] = $j;
	}

	for(my $j = 0; $j < $permLen; $j++) {
		my $idxToSwap;
	       	if($goodBad eq "good") {
			$idxToSwap = int(rand($permLen - $j)) + $j;
		} else {
			$idxToSwap = int(rand($permLen));
		}
		my $temp = $permutation[$j];
		$permutation[$j] = $permutation[$idxToSwap];
		$permutation[$idxToSwap] = $temp;
	}

	print $permsFile $permLen . "\n";
	print $permsFile join(" ", @permutation) . "\n";

	for(my $j = 0; $j < $permLen; $j++) {
		$averages[$j] = (($averages[$j] * $i) + $permutation[$j]) / ($i + 1);
	}
}

for(@averages) {
	print sprintf("%.2f", $_) . " ";
}
print "\n";

#print join(" ", sprintf(@averages) . "\n";
